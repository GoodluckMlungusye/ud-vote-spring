import React, { useState } from "react";
import axios from "axios";
import InputError from "@/Components/InputError";
import InputLabel from "@/Components/InputLabel";
import PrimaryButton from "@/Components/PrimaryButton";
import DangerButton from "@/Components/DangerButton";
import TextInput from "@/Components/TextInput";
import { Head } from "@inertiajs/react";
import SecondaryButton from "@/Components/SecondaryButton";
import { Link } from "@inertiajs/react";

const EditVoter = (props) => {
    const updateUrl = `http://127.0.0.1:8000/api/voter/update/${props.voter.id}`;
    const deleteUrl = `http://127.0.0.1:8000/api/voter/delete/${props.voter.id}`;

    const collegeData = props.college;
    const voter = props.voter;

    const [voterData, setVoterData] = useState({
        college_id: props.voter.college_id,
        first_name: props.voter.first_name,
        second_name: props.voter.second_name,
        last_name: props.voter.last_name,
        college_name: props.voter.college_name,
        registration_number: props.voter.registration_number,
        voter_email: props.voter.voter_email,
        voter_contact: props.voter.voter_contact,
    });

    const [voterImage, setVoterImage] = useState([]);

    const onChange = (e) => {
        e.persist();
        const newVoterData = { ...voterData };
        newVoterData[e.target.id] = e.target.value;
        setVoterData(newVoterData);
    };

    const onChangeImage = (e) => {
        setVoterImage({ voter_image: e.target.files[0] });
    };

    const editVoter = (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append("college_id", voterData.college_id);
        formData.append("first_name", voterData.first_name);
        formData.append("second_name", voterData.second_name);
        formData.append("last_name", voterData.last_name);
        formData.append("college_name", voterData.college_name);
        formData.append("registration_number", voterData.registration_number);
        formData.append("voter_email", voterData.voter_email);
        formData.append("voter_contact", voterData.voter_contact);
        formData.append("voter_image", voterImage.voter_image);

        axios.post(updateUrl, formData).then((response) => {
            console.log(response.data);
        });
    };

    const deleteVoter = () => {
        axios.delete(deleteUrl).then((response) => {
            console.log(response.data);
        });
    };

    return (
        <div className="min-h-screen bg-gray-100">
            <Head title="Voter Actions" />
            <div className="pt-20">
                <div className="mx-auto max-w-7xl sm:px-6 lg:px-8">
                    <div className="overflow-hidden bg-white shadow-sm sm:rounded-lg">
                        <div className="p-6 text-gray-900">
                            <form
                                encType="multipart/form-data"
                                onSubmit={editVoter}
                                className="mt-6 space-y-6"
                            >
                                <InputLabel
                                    htmlFor="college_id"
                                    value="College"
                                />

                                <select
                                    name="college_id"
                                    id="college_id"
                                    onChange={onChange}
                                    value={voterData.college_id}
                                    className="block w-full mt-1"
                                >
                                    {voter && <option>{voter.college.college_name}</option>}
                                    {collegeData &&
                                        collegeData.map((item) => (
                                            <option
                                                value={item.id}
                                                key={item.id}
                                            >
                                                {item.college_name}
                                            </option>
                                        ))}
                                </select>

                                 <InputError
                                    // message={category_name}
                                    className="mt-2"
                                />

                                <InputLabel
                                    htmlFor="first_name"
                                    value="First name"
                                />

                                <TextInput
                                    id="first_name"
                                    onChange={onChange}
                                    type="text"
                                    className="block w-full mt-1"
                                    name="first_name"
                                    value={voterData.first_name}
                                />

                                <InputError
                                    // message={category_name}
                                    className="mt-2"
                                />
                                <InputLabel
                                    htmlFor="second_name"
                                    value="Second name"
                                />

                                <TextInput
                                    id="second_name"
                                    onChange={onChange}
                                    type="text"
                                    className="block w-full mt-1"
                                    name="second_name"
                                    value={voterData.second_name}
                                />

                                <InputError
                                    // message={number_of_contestants}
                                    className="mt-2"
                                />

                                <InputLabel
                                    htmlFor="last_name"
                                    value="Last name"
                                />

                                <TextInput
                                    id="last_name"
                                    onChange={onChange}
                                    type="text"
                                    className="block w-full mt-1"
                                    name="last_name"
                                    value={voterData.last_name}
                                />

                                <InputError
                                    // message={number_of_contestants}
                                    className="mt-2"
                                />

                                <InputLabel
                                    htmlFor="registration_number"
                                    value="Registration number"
                                />

                                <TextInput
                                    id="registration_number"
                                    onChange={onChange}
                                    type="text"
                                    className="block w-full mt-1"
                                    name="registration_number"
                                    value={voterData.registration_number}
                                />

                                <InputError
                                    // message={number_of_contestants}
                                    className="mt-2"
                                />

                                <InputLabel
                                    htmlFor="voter_email"
                                    value="Voter email"
                                />

                                <TextInput
                                    id="voter_email"
                                    onChange={onChange}
                                    type="text"
                                    className="block w-full mt-1"
                                    name="voter_email"
                                    value={voterData.voter_email}
                                />

                                <InputError
                                    // message={number_of_contestants}
                                    className="mt-2"
                                />

                                <InputLabel
                                    htmlFor="voter_contact"
                                    value="Voter contact"
                                />

                                <TextInput
                                    id="voter_contact"
                                    onChange={onChange}
                                    type="text"
                                    className="block w-full mt-1"
                                    name="voter_contact"
                                    value={voterData.voter_contact}
                                />

                                <InputError
                                    // message={number_of_contestants}
                                    className="mt-2"
                                />

                                <InputLabel
                                    htmlFor="voter_image"
                                    value="Voter image"
                                />

                                <TextInput
                                    id="voter_image"
                                    onChange={onChangeImage}
                                    type="file"
                                    className="block w-full mt-1"
                                    name="voter_image"
                                />

                                <InputError
                                    //  message={category_abbreviation}
                                    className="mt-2"
                                />

                                <div>
                                    <img
                                        src={`http://127.0.0.1:8000/storage/${props.voter.voter_image}`}
                                        alt={`${voterData.last_name}'s image`}
                                        className="w-52 h-40"
                                    />
                                </div>

                                <div className="flex justify-between">
                                    <div>
                                        <PrimaryButton className="mr-4">
                                            update
                                        </PrimaryButton>

                                        <Link href={route("all.voter")}>
                                            <SecondaryButton>
                                                back to voters
                                            </SecondaryButton>
                                        </Link>
                                    </div>

                                    <Link href={route("all.voter")}>
                                        <DangerButton onClick={deleteVoter}>
                                            delete this voter
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

export default EditVoter;
