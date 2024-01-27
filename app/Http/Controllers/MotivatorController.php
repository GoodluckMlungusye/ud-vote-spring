<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Motivator;
use Inertia\Inertia;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Storage;
use Illuminate\Support\Facades\File;



class MotivatorController extends Controller
{

    public function viewAllMotivators()
    {

        return Inertia::render('ViewComponents/Motivators');
    }


    public function getAllMotivators()
    {
        $motivators = Motivator::latest()->get();
        return response()->json([
            'motivators' => $motivators,
        ]);
    }


    public function viewPostMotivator()
    {
        return Inertia::render('PostComponents/PostMotivator');
    }


    public function postMotivator(Request $request)
    {

        //validate inputs
        $validator = Validator::make(
            $request->all(),

            [
                'motivator_name' => ['required', 'unique:motivators', 'max:255', 'String'],
                'quote' => ['required', 'string', 'unique:motivators'],
                'motivator_image' => ['image', 'mimes:jpeg,png,jpg'],
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


        //add data into Motivator model

        $motivator = new Motivator;

        $motivator->motivator_name = $request->motivator_name;
        $motivator->quote = $request->quote;

        if ($request->hasFile('motivator_image')) {

            $image_file = $request->file('motivator_image');
            $extension = $image_file->getClientOriginalExtension();
            $filename = time() . '.' . $extension;
            $image_destination = 'images/leaders/';
            $path = $image_file->storeAs($image_destination, $filename,"public");
            $motivator->motivator_image = $path;
        }

        $motivator->save();


        return response()->json(
            [
                "message" => "Quote added successfully",
                "motivator" => $motivator
            ]
        );
    }


    public function viewEditMotivator($motivatorId)
    {

        $motivator = Motivator::find($motivatorId);
        return Inertia::render('EditComponents/EditMotivator', [
            'motivator' => $motivator
        ]);
    }


    public function editMotivator(Request $request, $motivatorId)
    {

        $motivator = Motivator::find($motivatorId);

        if (!$motivator) {
            return response()->json([

                'message' => 'Quote not found',
            ]);
        }


        //update data into Motivator model

        $motivator->motivator_name = $request->motivator_name;
        $motivator->quote = $request->quote;

        if ($request->hasFile('motivator_image')) {

            $path = 'images/leaders/'.$motivator->motivator_image;

            if(File::exists(storage_path($path))){
                Storage::delete($path);
            }

            $image_file = $request->file('motivator_image');
            $extension = $image_file->getClientOriginalExtension();
            $filename = time() . '.' . $extension;
            $image_destination = 'images/leaders/';
            $path = $image_file->storeAs($image_destination, $filename,"public");
            $motivator->motivator_image = $path;
        }

        $motivator->update();



        return response()->json([

            'message' => 'Quote updated successfully',
            'updatedQuote' =>  $motivator
        ]);
    }



    public function deleteMotivator($motivatorId)
    {
        $deletedMotivator = Motivator::find($motivatorId);

        if (!$deletedMotivator) {

            return response()->json([

                'message' => 'Quote not found'
            ]);
        }

        Storage::delete('images/leaders/'.$deletedMotivator->motivator_image);
        $deletedMotivator->delete();

        return response()->json([

            'message' => 'Quote deleted successfully'
        ]);
    }
}
