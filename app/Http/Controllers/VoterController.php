<?php

namespace App\Http\Controllers;
use App\Models\Voter;
use App\Models\College;
use Illuminate\Http\Request;
use Inertia\Inertia;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Storage;
use Illuminate\Support\Facades\File;


class VoterController extends Controller
{


    public function viewAllVoters(){

        return Inertia::render('ViewComponents/Voters');
    }


    public function viewPostVoter(){

        $college = College::all();
        return Inertia::render('PostComponents/PostVoter',[
            'college' => $college
        ]);
    }


    public function viewEditVoter($voterId){

        $college = College::all();
        $voter = Voter::find($voterId);
        return Inertia::render('EditComponents/EditVoter',[
            'voter' => $voter,
            'college' => $college
        ]);
    }



    public function getAllVoters()
    {
        $voters = Voter::latest()->get();
        return response()->json([
            'voters' => $voters
        ]);
    }



    public function postVoter(Request $request)
    {

        //validate inputs
        $validator = Validator::make(
            $request->all(),

            [
                'college_id' => ['required'],
                'first_name' => ['required', 'String'],
                'second_name' => ['required', 'string'],
                'last_name' => ['required','String'],
                'registration_number' => ['required', 'string', 'unique:voters'],
                'voter_email' => ['required', 'unique:voters','String'],
                'voter_contact' => ['required', 'string', 'unique:voters'],
                'voter_image' => ['image', 'mimes:jpeg,png,jpg'],
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


        //add data into Voter model

        $voter = new Voter;

        $voter->college_id = $request->college_id;
        $voter->first_name = $request->first_name;
        $voter->second_name = $request->second_name;
        $voter->last_name = $request->last_name;
        $voter->registration_number = $request->registration_number;
        $voter->voter_email = $request->voter_email;
        $voter->voter_contact = $request->voter_contact;

        if ($request->hasFile('voter_image')) {

            $image_file = $request->file('voter_image');
            $extension = $image_file->getClientOriginalExtension();
            $filename = time() . '.' . $extension;
            $image_destination = 'images/voters/';
            $path = $image_file->storeAs($image_destination, $filename,"public");
            $voter->voter_image = $path;
        }

        $voter->save();


        return response()->json(
            [
                "message" => "Voter added successfully",
                "voter" => $voter
            ]
        );
    }




    public function editVoter(Request $request, $voterId)
    {

        $voter = Voter::find($voterId);

        if (!$voter) {
            return response()->json([
                'message' => 'Voter not found',
            ]);
        }

        //update data into Voter model
        $voter->college_id = $request->college_id;
        $voter->first_name = $request->first_name;
        $voter->second_name = $request->second_name;
        $voter->last_name = $request->last_name;
        $voter->registration_number = $request->registration_number;
        $voter->voter_email = $request->voter_email;
        $voter->voter_contact = $request->voter_contact;

        if ($request->hasFile('voter_image')) {

            $path = 'images/voters/'.$voter->voter_image;

            if(File::exists(storage_path($path))){
                Storage::delete($path);
            }
            $image_file = $request->file('voter_image');
            $extension = $image_file->getClientOriginalExtension();
            $filename = time() . '.' . $extension;
            $image_destination = 'images/voters/';
            $path = $image_file->storeAs($image_destination, $filename,"public");
            $voter->voter_image = $path;
        }

        $voter->update();


        return response()->json(
            [
                "message" => "Voter updated successfully",
                "updatedVoter" => $voter
            ]
        );
    }



    public function deleteVoter($voterId)
    {
        $deletedVoter = Voter::find($voterId);

        if (!$deletedVoter) {

            return response()->json([

                'message' => 'Voter not found'
            ]);
        }

        Storage::delete('images/voters/'.$deletedVoter->voter_image);
        $deletedVoter->delete();
        return response()->json([

            'message' => 'Voter deleted successfully'
        ]);
    }



}
