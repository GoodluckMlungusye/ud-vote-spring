<?php

namespace App\Http\Controllers;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use App\Models\VoterContestant;

class VoterContestantController extends Controller
{
    //
    public function vote(Request $request){

        $validator = Validator::make(
            $request->all(),

            [
                'voter_id' => ['required', 'integer'],
                'contestant_id' => ['required', 'integer'],
                'category_id' => ['required', 'integer'],
            ]
        );


        if ($validator->fails()) {
            return response()->json(
                [
                    "message" => "Validation error",
                    "errors" => $validator->errors()
                ]
            );
        }

        $checkCategory = VoterContestant::where(['category_id' => $request->category_id])->first();


        if ($checkCategory) {
            return response()->json(
                [
                    "success" => false,
                    "errorMessage" => "You already voted for this category"
            ]);
        }

        $voterContestant = new VoterContestant;
        $voterContestant->voter_id = $request->voter_id ;
        $voterContestant->contestant_id = $request->contestant_id;
        $voterContestant->category_id = $request->category_id;

        $voterContestant->save();

        return response()->json(
            [
                "message" => "Vote received successfully",
                "success" => true
            ]
        );

    }
}
