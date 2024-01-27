<?php

namespace App\Http\Controllers;
use App\Models\Category;
use Inertia\Inertia;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;


class CategoryController extends Controller
{


    public function viewAllCategories(){

        return Inertia::render('ViewComponents/Categories');
    }


    public function getAllCategories(){
        $categories = Category::latest()->get();
        return response()->json([
            'categories' => $categories,
        ]);
    }


    public function viewPostCategory(){
        return Inertia::render('PostComponents/PostCategory');
    }


    public function postCategory(Request $request){

        //validate inputs
        $validator = Validator::make($request->all(),

        [
            'category_name' => ['required', 'unique:categories', 'max:255','String'],
            'number_of_contestants' => ['required', 'integer'],
            'category_abbreviation' => ['required', 'unique:categories', 'max:5','String' ],
        ]);


        if($validator->fails()){
            return response()->json(
                [
                    "message" => "Validation error",
                    "errors" => $validator->errors()
                ]
            );
        }


        //add data into Category model

        $category = new Category;

        $category->category_name = $request->category_name;
        $category->number_of_contestants = $request->number_of_contestants;
        $category->category_abbreviation = $request->category_abbreviation;
        $category->save();


        return response()->json(
            [
                "message" => "Category added successfully",
                "category" => $category
            ]
        );

    }


    public function viewEditCategory($categoryId){

        $category = Category::find($categoryId);
        return Inertia::render('EditComponents/EditCategory',[
            'category' => $category
        ]);
    }


    public function editCategory(Request $request, $categoryId){

        $updatedCategory = Category::find($categoryId);

        if(!$updatedCategory){
            return response()->json([

                'message' => 'Category not found',
            ]);
        }


        $updatedCategory->update([

            'category_name' => $request->category_name,
            'number_of_contestants' => $request->number_of_contestants,
            'category_abbreviation' => $request->category_abbreviation
        ]);

        return response()->json([

            'message' => 'Category updated successfully',
            'updatedCategory' => $updatedCategory
        ]);

    }


    public function deleteCategory($categoryId){
        $deletedCategory = Category::find($categoryId);

        if(!$deletedCategory){

            return response()->json([

                'message' => 'Category not found'
            ]);
        }

        $deletedCategory->delete();
        return response()->json([

            'message' => 'Category deleted successfully'
        ]);
    }


}
