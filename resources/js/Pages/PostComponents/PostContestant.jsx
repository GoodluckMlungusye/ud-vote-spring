import React, { useState } from "react";
import axios from "axios";
import InputError from "@/Components/InputError";
import InputLabel from "@/Components/InputLabel";
import PrimaryButton from "@/Components/PrimaryButton";
import TextInput from "@/Components/TextInput";
import { Head } from "@inertiajs/react";
import SecondaryButton from "@/Components/SecondaryButton";
import { Link } from "@inertiajs/react";

const PostContestant = (props) => {
    const url = "http://127.0.0.1:8000/api/contestant";

    const voterData = props.voter;

    const categoryData = props.category;

    const [contestantData, setContestantData] = useState({
        voter_id: "",
        category_id: "",
        slogan: "",
    });

    const [contestantVideo, setContestantVideo] = useState([]);

    const onChange = (e) => {
        e.persist();
        const newContestantData = { ...contestantData };
        newContestantData[e.target.id] = e.target.value;
        setContestantData(newContestantData);
    };

    const onChangeVideo = (e) => {
        setContestantVideo({ video_url: e.target.files[0] });
    };

    const postContestant = (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append("voter_id", contestantData.voter_id);
        formData.append("category_id", contestantData.category_id);
        formData.append("slogan", contestantData.slogan);
        formData.append("video_url", contestantVideo.video_url);

        axios.post(url, formData).then((response) => {
            console.log(response.data);
        });
    };

    return (
        <div className="min-h-screen bg-gray-100">
            <Head title="Add Voter" />
            <div className="pt-20">
                <div className="mx-auto max-w-7xl sm:px-6 lg:px-8">
                    <div className="overflow-hidden bg-white shadow-sm sm:rounded-lg">
                        <div className="p-6 text-gray-900">
                            <form
                                encType="multipart/form-data"
                                onSubmit={postContestant}
                                className="mt-6 space-y-6"
                            >
                                <InputLabel htmlFor="voter_id" value="Name" />

                                <select
                                    name="voter_id"
                                    id="voter_id"
                                    onChange={onChange}
                                    value={contestantData.voter_id}
                                    className="block w-full mt-1"
                                >
                                    <option>Select Name</option>

                                    {voterData &&
                                        voterData.map((item) => (
                                            <option
                                                value={item.id}
                                                key={item.id}
                                            >
                                                {`${item.first_name} ${item.second_name} ${item.last_name}`}
                                            </option>
                                        ))}
                                </select>

                                <InputLabel
                                    htmlFor="category_id"
                                    value="Category"
                                />

                                <select
                                    name="category_id"
                                    id="category_id"
                                    onChange={onChange}
                                    value={contestantData.category_id}
                                    className="block w-full mt-1"
                                >
                                    <option>Select Category</option>
                                    {categoryData &&
                                        categoryData.map((item) => (
                                            <option
                                                value={item.id}
                                                key={item.id}
                                            >
                                                {item.category_name}
                                            </option>
                                        ))}
                                </select>

                                <InputLabel htmlFor="slogan" value="Slogan" />

                                <TextInput
                                    id="slogan"
                                    onChange={onChange}
                                    type="text"
                                    className="block w-full mt-1"
                                    name="slogan"
                                />

                                <InputError
                                    // message={category_name}
                                    className="mt-2"
                                />

                                <InputLabel
                                    htmlFor="video_url"
                                    value="Campaign video"
                                />

                                <TextInput
                                    id="video_url"
                                    onChange={onChangeVideo}
                                    type="file"
                                    className="block w-full mt-1"
                                    name="video_url"
                                />

                                <InputError
                                    //  message={category_abbreviation}
                                    className="mt-2"
                                />

                                <div className="flex justify-between">
                                    <PrimaryButton>add</PrimaryButton>

                                    <Link href={route("all.contestant")}>
                                        <SecondaryButton>
                                            back to contestants
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
};

export default PostContestant;
