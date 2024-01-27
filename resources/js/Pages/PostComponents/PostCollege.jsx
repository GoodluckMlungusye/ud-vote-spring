import React, {useState} from 'react';
import axios from "axios";
import InputError from "@/Components/InputError";
import InputLabel from "@/Components/InputLabel";
import PrimaryButton from "@/Components/PrimaryButton";
import TextInput from "@/Components/TextInput";
import { Head } from "@inertiajs/react";
import SecondaryButton from "@/Components/SecondaryButton";
import { Link } from "@inertiajs/react";


const PostCollege = () => {


    const url = "http://127.0.0.1:8000/api/college";

    const [collegeData, setCollegeData] = useState({

        college_name: "",

    });

    const [collegeImage, setCollegeImage] = useState([]);


    const onChange = (e) => {
        e.persist();
        const newCollegeData = { ...collegeData };
        newCollegeData[e.target.id] = e.target.value;
        setCollegeData(newCollegeData);
    };


    const onChangeImage = (e) => {
        setCollegeImage({college_image: e.target.files[0]});
    };



    const postCollege = (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append('college_name',collegeData.college_name);
        formData.append('college_image',collegeImage.college_image);


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
                                encType='multipart/form-data'
                                onSubmit={postCollege}
                                className="mt-6 space-y-6"
                            >
                                <InputLabel
                                    htmlFor="college_name"
                                    value="College name"
                                />

                                <TextInput
                                    id="college_name"
                                    onChange={onChange}
                                    type="text"
                                    className="block w-full mt-1"
                                    name="college_name"
                                />

                                <InputError
                                    // message={category_name}
                                    className="mt-2"
                                />


                                <InputLabel
                                    htmlFor="college_image"
                                    value="College image"
                                />

                                <TextInput
                                    id="college_image"
                                    onChange={onChangeImage}
                                    type="file"
                                    className="block w-full mt-1"
                                    name="college_image"
                                />

                                <InputError
                                    //  message={category_abbreviation}
                                    className="mt-2"
                                />

                                <div className="flex justify-between">
                                    <PrimaryButton>add</PrimaryButton>

                                    <Link href={route("all.college")}>
                                        <SecondaryButton>
                                            back to colleges
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

export default PostCollege
