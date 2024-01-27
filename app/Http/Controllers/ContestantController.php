<?php


namespace App\Http\Controllers;
use App\Models\Contestant;
use App\Models\Voter;
use App\Models\Category;
use Illuminate\Http\Request;
use Inertia\Inertia;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\Storage;
use Illuminate\Support\Facades\File;
use Symfony\Component\HttpFoundation\StreamedResponse;


class ContestantController extends Controller
{



    public function viewAllContestants(){

        return Inertia::render('ViewComponents/Contestants');
    }


    public function viewPostContestant(){

        $voter = Voter::all();
        $category = Category::all();

        return Inertia::render('PostComponents/PostContestant',[
            'voter' => $voter,
            'category' => $category

        ]);
    }


    public function viewEditContestant($contestantId){

        $voter = Voter::all();
        $category = Category::all();

        $contestant = Contestant::find($contestantId);
        return Inertia::render('EditComponents/EditContestant',[
            'contestant' => $contestant,
            'voter' => $voter,
            'category' => $category

        ]);
    }


    public function getAllContestants()
    {
        $contestants = Contestant::latest()->get();
        return response()->json([
            'contestants' => $contestants ,
        ]);
    }



    public function postContestant(Request $request)
    {

        //validate inputs
        $validator = Validator::make(
            $request->all(),

            [
                'voter_id' => ['required'],
                'category_id' => ['required'],
                'slogan' => ['required', 'String'],
                'video_url' => ['required', 'mimes:mp4','max:100000'],
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


        //add data into Contestant model

        $contestant = new Contestant;

        $contestant->voter_id = $request->voter_id;
        $contestant->category_id = $request->category_id;
        $contestant->slogan = $request->slogan;

        if ($request->hasFile('video_url')) {

            $video_file = $request->file('video_url');
            $extension = $video_file->getClientOriginalExtension();
            $filename = time() . '.' . $extension;
            $video_destination = 'videos/contestants/';
            $path = $video_file->storeAs($video_destination, $filename,"public");
            $contestant->video_url = $path;
        }

        $contestant->save();


        return response()->json(
            [
                "message" => "Contestant added successfully",
                "contestant" => $contestant
            ]
        );
    }



    public function getVideo(Request $request){

        if (Storage::disk('public')->exists($request->video_url)) {
            $stream = Storage::disk('public')->readStream($request->video_url);

            return response()->stream(function () use ($stream) {
                    fpassthru($stream);
                    if (is_resource($stream)) {
                        fclose($stream);
                    }
                }, 200,[
                    'Content-Type' => Storage::mimeType($request->video_url),
                    'Content-Disposition'   => 'attachment; filename="' . basename($request->video_url) . '"',
                ]);
        } else {
            abort(404);
        }

    }



    public function editContestant(Request $request, $contestantId)

    {
        $contestant = Contestant::find($contestantId);

        if (!$contestant) {
            return response()->json([
                'message' => 'Contestant not found',
            ]);
        }

        //update data into Contestant model
        $contestant->voter_id = $request->voter_id;
        $contestant->category_id = $request->category_id;
        $contestant->slogan = $request->slogan;

        if ($request->hasFile('video_url')) {

            $path = 'videos/contestants/'.$contestant->video_url;

            if(File::exists(storage_path($path))){
                File::delete($path);
            }
            $video_file = $request->file('video_url');
            $extension = $video_file->getClientOriginalExtension();
            $filename = time() . '.' . $extension;
            $video_destination = 'videos/contestants/';
            $path = $video_file->storeAs($video_destination, $filename,"public");
            $contestant->video_url = $path;
        }

        $contestant->update();


        return response()->json(
            [
                "message" => "Contestant updated successfully",
                "contestant" => $contestant
            ]
        );
    }



    public function deleteContestant($contestantId)
    {
        $deletedContestant = Contestant::find($contestantId);

        if (!$deletedContestant) {

            return response()->json([

                'message' => 'Contestant not found'
            ]);
        }


        Storage::delete('videos/contestants/'. $deletedContestant->video_url);
        $deletedContestant->delete();
        return response()->json([

            'message' => 'Contestant deleted successfully'
        ]);
    }


}
