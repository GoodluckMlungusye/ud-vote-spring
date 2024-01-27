import { useState } from "react";
import axios from "axios";
import InputError from "@/Components/InputError";
import InputLabel from "@/Components/InputLabel";
import PrimaryButton from "@/Components/PrimaryButton";
import TextInput from "@/Components/TextInput";
import { Head } from "@inertiajs/react";
import SecondaryButton from "@/Components/SecondaryButton";
import { Link } from "@inertiajs/react";

function PostCategory() {
    const url = "http://127.0.0.1:8000/api/category";

    const [categoryData, setCategoryData] = useState({
        category_name: "",
        number_of_contestants: "",
        category_abbreviation: "",
    });

    const onChange = (e) => {
        const newCategoryData = { ...categoryData };
        newCategoryData[e.target.id] = e.target.value;
        setCategoryData(newCategoryData);
    };

    const postCategory = (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append('category_name',categoryData.category_name);
        formData.append('number_of_contestants',parseInt(
            categoryData.number_of_contestants
        ));
        formData.append('category_abbreviation',categoryData.category_abbreviation);

        axios
            .post(url,formData)
            .then((response) => {
                console.log(response.data);
            });
    }

    return (
        <div className="min-h-screen bg-gray-100">
            <Head title="Add Voting Category" />
            <div className="pt-40">
                <div className="mx-auto max-w-7xl sm:px-6 lg:px-8">
                    <div className="overflow-hidden bg-white shadow-sm sm:rounded-lg">
                        <div className="p-6 text-gray-900">
                            <form
                                onSubmit={postCategory}
                                className="mt-6 space-y-6"
                            >
                                <InputLabel
                                    htmlFor="category_name"
                                    value="Category name"
                                />

                                <TextInput
                                    id="category_name"
                                    onChange={onChange}
                                    type="text"
                                    className="block w-full mt-1"
                                    autoComplete="category_name"
                                    name="category_name"
                                    value={categoryData.category_name}
                                />

                                <InputError
                                    // message={category_name}
                                    className="mt-2"
                                />

                                <InputLabel
                                    htmlFor="number_of_contestants"
                                    value="Number of contestants"
                                />

                                <TextInput
                                    id="number_of_contestants"
                                    onChange={onChange}
                                    type="text"
                                    className="block w-full mt-1"
                                    autoComplete="number_of_contestants"
                                    name="number_of_contestants"
                                    value={categoryData.number_of_contestants}
                                />

                                <InputError
                                    // message={number_of_contestants}
                                    className="mt-2"
                                />

                                <InputLabel
                                    htmlFor="category_abbreviation"
                                    value="Category abbreviation"
                                />

                                <TextInput
                                    id="category_abbreviation"
                                    onChange={onChange}
                                    type="text"
                                    className="block w-full mt-1"
                                    autoComplete="category_abbreviation"
                                    name="category_abbreviation"
                                    value={categoryData.category_abbreviation}
                                />

                                <InputError
                                    // message={category_abbreviation}
                                    className="mt-2"
                                />

                                <div className="flex justify-between">
                                    <PrimaryButton>add</PrimaryButton>

                                    <Link href={route("all.category")}>
                                        <SecondaryButton>
                                            back to categories
                                        </SecondaryButton>
                                    </Link>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default PostCategory;
