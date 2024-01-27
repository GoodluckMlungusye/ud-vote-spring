<?php

namespace App\Http\Controllers;
use Illuminate\Http\Request;
use App\Models\Voter;
use App\Models\VoterContestant;
use App\Models\CollegeYear;

class DataController extends Controller
{
    //
    public function getCategoryContestants(Request $request)
    {

            $categoryContestants = Voter::join('contestants', 'voters.id', '=', 'contestants.voter_id')
            ->join('categories', 'categories.id', '=', 'contestants.category_id')
            ->where('categories.category_name', $request->category_name)
            ->select('voters.*','categories.*', 'contestants.*')
            ->get();

        return response()->json([
            'categoryContestants' => $categoryContestants
        ]);


    }



    public function getSelectedContestant(Request $request)
    {

        $selectedContestant = Voter::join('contestants', 'voters.id', '=', 'contestants.voter_id')
            ->join('categories', 'categories.id', '=', 'contestants.category_id')
            ->where('voters.registration_number', $request->registration_number)
            ->select('voters.*','categories.*', 'contestants.*')
            ->get();

        return response()->json([
            'categoryContestants' => $selectedContestant
        ]);


    }


    public function getResults(Request $request){

        $countVoters = Voter::count();

        $displayContestants= Voter::join('contestants', 'voters.id', '=', 'contestants.voter_id')
        ->join('categories', 'categories.id', '=', 'contestants.category_id')
        ->where('categories.category_name', $request->category_name)
        ->select('voters.*')
        ->get();

        $countContestants = Voter::join('contestants', 'voters.id', '=', 'contestants.voter_id')
        ->join('categories', 'categories.id', '=', 'contestants.category_id')
        ->where('categories.category_name',$request->category_name)
        ->count();

        $countCategoryTotalVotes = VoterContestant::join('categories', 'voter_contestants.category_id', '=', 'categories.id')
        ->where('categories.category_name', $request->category_name)
        ->count();


        return response()->json([
            'countVoters' => $countVoters,
            'displayContestants' => $displayContestants,
            'countContestants' => $countContestants,
            'countCategoryTotalVotes' => $countCategoryTotalVotes,
        ]);

    }


    public function getContestantVotes(Request $request){

        $countContestantVotes = VoterContestant::where('contestant_id', $request->contestant_id)->count();

        return response()->json([

        'countContestantVotes' => $countContestantVotes

        ]);

    }


    public function getVoteYears(){

        $getYears = CollegeYear::distinct('year')->pluck('year');

        return response()->json([

            'getYears' => $getYears,

        ]);

    }



    public function getStats(Request $request){

        $collegeStats = CollegeYear::where('year', $request->year)->get();

        return response()->json([

            'collegeStats' => $collegeStats,

        ]);

    }

}
