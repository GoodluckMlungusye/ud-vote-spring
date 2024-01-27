<?php

use App\Http\Controllers\ProfileController;
use App\Http\Controllers\VoterController;
use App\Http\Controllers\CategoryController;
use App\Http\Controllers\CollegeController;
use App\Http\Controllers\ContestantController;
use App\Http\Controllers\YearController;
use App\Http\Controllers\MotivatorController;
use Illuminate\Foundation\Application;
use Illuminate\Support\Facades\Route;
use Inertia\Inertia;


Route::get('/', function () {
    return Inertia::render('Welcome', [
        'canLogin' => Route::has('login'),
        'canRegister' => Route::has('register'),
        'laravelVersion' => Application::VERSION,
        'phpVersion' => PHP_VERSION,
    ]);
});


Route::get('/dashboard', function () {
    return Inertia::render('Dashboard');
})->middleware(['auth', 'verified'])->name('dashboard');



Route::middleware('auth','verified')->group(function(){

    //VOTER

    Route::controller(VoterController::class)->group(function() {

        Route::get('/voter/all','viewAllVoters')->name('all.voter');

        Route::get('/voter/post', 'viewPostVoter')->name('post.voter');

        Route::get('/voter/edit/{voterId}', 'viewEditVoter');

    });

    //END VOTER



    //CATEGORY

    Route::controller(CategoryController::class)->group(function(){

        Route::get('/category/all', 'viewAllCategories')->name('all.category');

        Route::get('/category/post', 'viewPostCategory')->name('post.category');

        Route::get('/category/edit/{categoryId}', 'viewEditCategory');

    });

    //END CATEGORY



    //MOTIVATOR

    Route::controller(MotivatorController::class)->group(function(){

        Route::get('/motivator/all', 'viewAllMotivators')->name('all.motivator');

        Route::get('/motivator/post', 'viewPostMotivator')->name('post.motivator');

        Route::get('/motivator/edit/{motivatorId}', 'viewEditMotivator');

    });

    //END MOTIVATOR



    //COLLEGE

    Route::controller(CollegeController::class)->group(function(){

        Route::get('/college/all', 'viewAllColleges')->name('all.college');

        Route::get('/college/post', 'viewPostCollege')->name('post.college');

        Route::get('/college/edit/{collegeId}', 'viewEditCollege');

    });

    //END COLLEGE



    //CONTESTANT

    Route::controller(ContestantController::class)->group(function(){

        Route::get('/contestant/all', 'viewAllContestants')->name('all.contestant');

        Route::get('/contestant/post', 'viewPostContestant')->name('post.contestant');

        Route::get('/contestant/edit/{contestantId}', 'viewEditContestant');

    });

    //END CONTESTANT



    //YEAR

    Route::controller(YearController::class)->group(function(){

        Route::get('/year/all', 'viewAllYears')->name('all.year');

        Route::get('/year/post', 'viewPostYear')->name('post.year');

        Route::get('/year/edit/{yearId}', 'viewEditYear');

    });


    //END YEAR


});



Route::middleware('auth')->group(function () {

    Route::get('/profile', [ProfileController::class, 'edit'])->name('profile.edit');
    Route::patch('/profile', [ProfileController::class, 'update'])->name('profile.update');
    Route::delete('/profile', [ProfileController::class, 'destroy'])->name('profile.destroy');


});




require __DIR__.'/auth.php';
