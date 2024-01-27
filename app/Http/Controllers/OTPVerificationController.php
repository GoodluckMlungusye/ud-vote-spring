<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Mail;
use App\Models\Voter;
use App\Mail\OtpMail;
use App\Models\OTPVerification;
use Carbon\Carbon;


class OTPVerificationController extends Controller
{

    public function generateOTP(Request $request)
    {

        //validate inputs
        $validator = Validator::make(
            $request->all(),

            [
                'registration_number' => ['required', 'exists:voters'],
            ]
        );


        if ($validator->fails()) {
            return response()->json(
                [
                    "success" => false,
                    "errorMessage" => "registration number is invalid",
                ]
            );
        }

        //generate OTP
        $generatedOTP = $this->makeOTP($request->registration_number);

         //generate OTP email
         $voter = Voter::where('registration_number', $request->registration_number)->first();

         if($voter){
            Mail::to($voter->voter_email)->send(new OtpMail($generatedOTP));
            return response()->json(
                [
                    "voter_id" => $generatedOTP->voter_id,
                    "success" => true,
                    "message" => "verification code sent to your email",

                ]
            );

         }else{
            return response()->json(
                [

                    "success" => false,
                    "errorMessage" => "registration number does not exist",

                ]
            );
         }






    }


    public function makeOTP($registration_number)
    {

        $voter = Voter::where('registration_number', $registration_number)->first();

        $OTPverifier = OTPVerification::where('voter_id', $voter->id)->latest()->first();

        $now = Carbon::now();

        if ($OTPverifier && $now->isBefore($OTPverifier->expire_at)) {
            return $OTPverifier;
        }

        //create new OTP
        return OTPVerification::create([
            'voter_id' => $voter->id,
            'otp' => rand(123456, 999999),
            'expire_at' => Carbon::now()->addMinutes(10)
        ]);
    }


    public function verifyOTP(Request $request)
    {
        //validate inputs
        $validator = Validator::make(
            $request->all(),

            [
                'voter_id' => ['required', 'exists:voters,id'],
                'otp' => ['required']
            ]
        );


        if ($validator->fails()) {
            return response()->json(
                [
                    "errorMessage" => "invalid OTP",
                    "success" => false
                ]
            );
        }

        $verifiedOTP = OTPVerification::where('voter_id', $request->voter_id)->latest()->first();

        $now = Carbon::now();

        if (!$verifiedOTP) {
            return response()->json(
                [
                    "errorMessage" => "invalid OTP",
                    "success" => false
                ]
            );
        }

        if ($verifiedOTP && $now->isAfter($verifiedOTP->expire_at)) {
            return response()->json(
                [
                    "errorMessage" => "Your OTP has been expired",
                    "success" => false
                ]
            );
        }

        if ($verifiedOTP->otp == $request->otp) {
            return response()->json(
                [
                    "message" => "Your verification is successful",
                    "success" => true
                ]
            );
        }
    }
}

