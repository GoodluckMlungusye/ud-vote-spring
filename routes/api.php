<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Models\User;
use App\Http\Controllers\VoterController;
use App\Http\Controllers\CategoryController;
use App\Http\Controllers\CollegeController;
use App\Http\Controllers\ContestantController;
use App\Http\Controllers\OTPVerificationController;
use App\Http\Controllers\YearController;
use App\Http\Controllers\MotivatorController;
use App\Http\Controllers\VoterContestantController;
use App\Http\Controllers\DataController;

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});


Route::get('/admin', function(){
    $admin = User::latest()->get();
    return response()->json([
        'admin' => $admin,
    ]);
});



// CATEGORY

Route::controller(CategoryController::class)->group(function() {

    Route::get('/categories','getAllCategories');

    Route::post('/category', 'postCategory');

    Route::post('/category/update/{categoryId}', 'editCategory');

    Route::delete('/category/delete/{categoryId}', 'deleteCategory');

});


//END CATEGORY




//MOTIVATOR

Route::controller(MotivatorController::class)->group(function() {

    Route::get('/motivators','getAllMotivators');

    Route::post('/motivator', 'postMotivator');

    Route::post('/motivator/update/{motivatorId}', 'editMotivator');

    Route::delete('/motivator/delete/{motivatorId}', 'deleteMotivator');

});


//END MOTIVATOR



//VOTER

Route::controller(VoterController::class)->group(function(){

    Route::get('/voters','getAllVoters');

    Route::post('/voter', 'postVoter');

    Route::post('/voter/update/{voterId}', 'editVoter');

    Route::delete('/voter/delete/{voterId}', 'deleteVoter');

});


//END VOTER




//CONTESTANT

Route::controller(ContestantController::class)->group(function(){

    Route::get('/contestants','getAllContestants');

    Route::post('/contestant', 'postContestant');

    Route::post('/contestant/update/{contestantId}', 'editContestant');

    Route::delete('/contestant/delete/{contestantId}', 'deleteContestant');

    Route::get('/video','getVideo');

});

//END CONTESTANT




//COLLEGE

Route::controller(CollegeController::class)->group(function(){

    Route::get('/colleges','getAllColleges');

    Route::post('/college', 'postCollege');

    Route::post('/college/update/{collegeId}', 'editCollege');

    Route::delete('/college/delete/{collegeId}', 'deleteCollege');

});


//END COLLEGE



// YEAR

Route::controller(YearController::class)->group(function(){

    Route::get('/years','getAllYears');

    Route::post('/year', 'postYear');

    Route::post('/year/update/{yearId}', 'editYear');

    Route::delete('/year/delete/{yearId}', 'deleteYear');

});

//END YEAR





// OTP

Route::controller(OTPVerificationController::class)->group(function(){

    Route::post('/otp','generateOTP');

    Route::post('/verify', 'verifyOTP');

});

//END OTP



//DISPLAY_DATA

Route::controller(DataController::class)->group(function(){

    Route::get('/categoryContestants','getCategoryContestants');
    Route::get('/selectedContestant','getSelectedContestant');
    Route::get('/results','getResults');
    Route::get('/contestantVotes','getContestantVotes');
    Route::get('/voteYear','getVoteYears');
    Route::get('/stats','getStats');

});



//END DISPLAY_DATA



 // VOTE COUNT

Route::post('/vote', [VoterContestantController::class,'vote']);

//END VOTE COUNT








