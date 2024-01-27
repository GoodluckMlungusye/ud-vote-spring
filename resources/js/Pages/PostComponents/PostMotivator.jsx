import React, {useState} from 'react';
import axios from "axios";
import InputError from "@/Components/InputError";
import InputLabel from "@/Components/InputLabel";
import PrimaryButton from "@/Components/PrimaryButton";
import TextInput from "@/Components/TextInput";
import { Head } from "@inertiajs/react";
import SecondaryButton from "@/Components/SecondaryButton";
import { Link } from "@inertiajs/react";


function PostMotivator() {

    const url = "http://127.0.0.1:8000/api/motivator";

    const [motivatorData, setMotivatorData] = useState({

        motivator_name: "",
        quote: ""

    });

    const [leaderImage, setLeaderImage] = useState([]);


    const onChange = (e) => {
        e.persist();
        const newMotivatorData = { ...motivatorData };
        newMotivatorData[e.target.id] = e.target.value;
        setMotivatorData(newMotivatorData);
    };


    const onChangeImage = (e) => {
        setLeaderImage({motivator_image: e.target.files[0]});
    };



    const postMotivator = (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append('motivator_name',motivatorData.motivator_name);
        formData.append('quote',motivatorData.quote);
        formData.append('motivator_image',leaderImage.motivator_image);


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
                                onSubmit={postMotivator}
                                className="mt-6 space-y-6"
                            >
                                <InputLabel
                                    htmlFor="motivator_name"
                                    value="Motivator name"
                                />

                                <TextInput
                                    id="motivator_name"
                                    onChange={onChange}
                                    type="text"
                                    className="block w-full mt-1"
                                    name="motivator_name"
                                />

                                <InputError
                                    // message={category_name}
                                    className="mt-2"
                                />

                                <InputLabel
                                    htmlFor="quote"
                                    value="Quote"
                                />

                                <TextInput
                                    id="quote"
                                    onChange={onChange}
                                    type="text"
                                    className="block w-full mt-1"
                                    name="quote"
                                />

                                <InputError
                                    // message={number_of_contestants}
                                    className="mt-2"
                                />

                                <InputLabel
                                    htmlFor="motivator_image"
                                    value="Motivator image"
                                />

                                <TextInput
                                    id="motivator_image"
                                    onChange={onChangeImage}
                                    type="file"
                                    className="block w-full mt-1"
                                    name="motivator_image"
                                />

                                <InputError
                                    //  message={category_abbreviation}
                                    className="mt-2"
                                />

                                <div className="flex justify-between">
                                    <PrimaryButton>add</PrimaryButton>

                                    <Link href={route("all.motivator")}>
                                        <SecondaryButton>
                                            back to quotes
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

export default PostMotivator

