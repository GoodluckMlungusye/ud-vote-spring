import React, { useState } from "react";
import axios from "axios";
import InputError from "@/Components/InputError";
import InputLabel from "@/Components/InputLabel";
import PrimaryButton from "@/Components/PrimaryButton";
import DangerButton from "@/Components/DangerButton";
import TextInput from "@/Components/TextInput";
import { Head } from "@inertiajs/react";
import { Link } from "@inertiajs/react";
import SecondaryButton from "@/Components/SecondaryButton";

const EditMotivator = (props) => {
    const updateUrl = `http://127.0.0.1:8000/api/motivator/update/${props.motivator.id}`;
    const deleteUrl = `http://127.0.0.1:8000/api/motivator/delete/${props.motivator.id}`;

    const [motivatorData, setMotivatorData] = useState({
        motivator_name: props.motivator.motivator_name,
        quote: props.motivator.quote,
    });

    const [leaderImage, setLeaderImage] = useState([]);

    const onChange = (e) => {
        e.persist();
        const newMotivatorData = { ...motivatorData };
        newMotivatorData[e.target.id] = e.target.value;
        setMotivatorData(newMotivatorData);
    };

    const onChangeImage = (e) => {
        setLeaderImage({ motivator_image: e.target.files[0] });
    };

    const editMotivator = (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append("motivator_name", motivatorData.motivator_name);
        formData.append("quote", motivatorData.quote);
        formData.append("motivator_image", leaderImage.motivator_image);

        axios.post(updateUrl, formData).then((response) => {
            console.log(response.data);
        });
    };

    const deleteMotivator = () => {
        axios.delete(deleteUrl).then((response) => {
            console.log(response.data);
        });
    };

    return (
        <div className="min-h-screen bg-gray-100">
            <Head title="Voting Category Actions" />
            <div className="pt-28">
                <div className="mx-auto max-w-7xl sm:px-6 lg:px-8">
                    <div className="overflow-hidden bg-white shadow-sm sm:rounded-lg">
                        <div className="p-6 text-gray-900">
                            <form
                                encType="multipart/form-data"
                                onSubmit={editMotivator}
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
                                    value={motivatorData.motivator_name}
                                />

                                <InputError
                                    // message={category_name}
                                    className="mt-2"
                                />

                                <InputLabel htmlFor="quote" value="Quote" />

                                <TextInput
                                    id="quote"
                                    onChange={onChange}
                                    type="text"
                                    className="block w-full mt-1"
                                    name="quote"
                                    value={motivatorData.quote}
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

                                <div>
                                    <img
                                        src={`http://127.0.0.1:8000/storage/${props.motivator.motivator_image}`}
                                        alt={`${motivatorData.motivator_name}'s image`}
                                        className="w-52 h-40"
                                    />
                                </div>

                                <div className="flex justify-between">
                                    <div>
                                        <PrimaryButton className="mr-4">
                                            update
                                        </PrimaryButton>

                                        <Link href={route("all.motivator")}>
                                            <SecondaryButton>
                                                back to quotes
                                            </SecondaryButton>
                                        </Link>
                                    </div>

                                    <Link href={route("all.motivator")}>
                                        <DangerButton onClick={deleteMotivator}>
                                            delete this quote
                                        </DangerButton>
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

export default EditMotivator;
