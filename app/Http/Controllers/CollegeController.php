<?php

namespace App\Http\Controllers;
use App\Models\College;
use Illuminate\Http\Request;
use Inertia\Inertia;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Storage;
use Illuminate\Support\Facades\File;

class CollegeController extends Controller
{


    public function viewAllColleges(){

        return Inertia::render('ViewComponents/Colleges');
    }


    public function viewPostCollege(){
        return Inertia::render('PostComponents/PostCollege');
    }


    public function viewEditCollege($collegeId){

        $college = College::find($collegeId);
        return Inertia::render('EditComponents/EditCollege',[
            'college' => $college
        ]);
    }



    public function getAllColleges()
    {
        $colleges = College::latest()->get();
        return response()->json([
            'colleges' => $colleges ,
        ]);
    }



    public function postCollege(Request $request)
    {

        //validate inputs
        $validator = Validator::make(
            $request->all(),

            [
                'college_name' => ['required', 'String'],
                'college_image' => ['image', 'mimes:jpeg,png,jpg'],
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


        //add data into College model

        $college = new College;

        $college->college_name = $request->college_name;

        if ($request->hasFile('college_image')) {

            $image_file = $request->file('college_image');
            $extension = $image_file->getClientOriginalExtension();
            $filename = time() . '.' . $extension;
            $image_destination = 'images/colleges/';
            $path = $image_file->storeAs($image_destination, $filename,"public");
            $college->college_image = $path;
        }

        $college->save();


        return response()->json(
            [
                "message" => "College added successfully",
                "college" => $college
            ]
        );
    }



    public function editCollege(Request $request, $collegeId)

    {
        $college = College::find($collegeId);

        if (!$college) {
            return response()->json([
                'message' => 'College not found',
            ]);
        }

        //update data into Contestant model
        $college->college_name = $request->college_name;

        if ($request->hasFile('college_image')) {

            $path = 'images/colleges/'.$college->college_image;

            if(File::exists(storage_path($path))){
                Storage::delete($path);
            }
            $image_file = $request->file('college_image');
            $extension = $image_file->getClientOriginalExtension();
            $filename = time() . '.' . $extension;
            $image_destination = 'images/colleges/';
            $path = $image_file->storeAs($image_destination, $filename,"public");
            $college->college_image = $path;
        }

        $college->update();


        return response()->json(
            [
                "message" => "College updated successfully",
                "college" => $college
            ]
        );
    }



    public function deleteCollege($collegeId)
    {
        $deletedCollege = College::find($collegeId);

        if (!$deletedCollege) {

            return response()->json([

                'message' => 'College not found'
            ]);
        }

        Storage::delete('images/colleges/'.$deletedCollege->college_image);
        $deletedCollege->delete();

        return response()->json([

            'message' => 'College deleted successfully'
        ]);
    }



}
