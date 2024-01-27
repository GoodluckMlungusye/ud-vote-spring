<?php


namespace App\Http\Controllers;

use App\Models\CollegeYear;
use App\Models\College;
use Illuminate\Http\Request;
use Inertia\Inertia;
use Illuminate\Support\Facades\Validator;

class YearController extends Controller
{
    public function viewAllYears()
    {

        return Inertia::render('ViewComponents/Years');
    }


    public function viewPostYear()
    {
        $college = College::all();
        return Inertia::render('PostComponents/PostYear', [
            'college' => $college
        ]);
    }

    public function viewEditYear($yearId)
    {
        $college = College::all();
        $year = CollegeYear::find($yearId);
        return Inertia::render('EditComponents/EditYear', [
            'year' => $year,
            'college' => $college
        ]);
    }

    public function getAllYears()
    {

        $years = CollegeYear::latest()->get();
        return response()->json([
            'years' => $years,
        ]);
    }




    public function postYear(Request $request)
    {

        // validate inputs
        $validator = Validator::make(
            $request->all(),

            [
                'college_id' => ['required', 'integer'],
                'year' => ['required', 'integer'],
                'registered_students' => ['required', 'integer'],
                'universal_contestants' => ['required', 'integer'],
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

        $checkYear = CollegeYear::where(['year' => $request->year, 'college_id' => $request->college_id])->first();

        if ($checkYear) {
            return response()->json(
                [
                    'message' => 'Integrity Error',
                    'errors' => 'College Year already exists'
            ]);
        }

        //add data into Year model

        $year = new CollegeYear;

        $year->college_id = $request->college_id;
        $year->year = $request->year;
        $year->registered_students = $request->registered_students;
        $year->universal_contestants = $request->universal_contestants;

        $year->save();


        return response()->json(
            [
                "message" => "Year added successfully",
                "year" => $year
            ]
        );
    }



    public function editYear(Request $request, $yearId)

    {
        $year = CollegeYear::find($yearId);

        if (!$year) {
            return response()->json([
                'message' => 'Year not found',
            ]);
        }

        //update data into Year model

        $year->college_id = $request->college_id;
        $year->year = $request->year;
        $year->registered_students = $request->registered_students;
        $year->universal_contestants = $request->universal_contestants;


        $year->update();


        return response()->json(
            [
                "message" => "Year updated successfully",
                "vote_year" => $year
            ]
        );
    }


    public function deleteYear($yearId)
    {
        $deletedYear = CollegeYear::find($yearId);

        if (!$deletedYear) {

            return response()->json([

                'message' => 'Year not found'
            ]);
        }

        $deletedYear->delete();
        return response()->json([

            'message' => 'Year deleted successfully'
        ]);
    }
}
