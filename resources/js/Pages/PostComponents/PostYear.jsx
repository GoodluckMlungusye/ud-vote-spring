import { useState } from "react";
import axios from "axios";
import InputError from "@/Components/InputError";
import InputLabel from "@/Components/InputLabel";
import PrimaryButton from "@/Components/PrimaryButton";
import TextInput from "@/Components/TextInput";
import { Head } from "@inertiajs/react";
import SecondaryButton from "@/Components/SecondaryButton";
import { Link } from "@inertiajs/react";

const PostYear = (props) => {
    const url = "http://127.0.0.1:8000/api/year";

    const collegeData = props.college;

    const [yearData, setYearData] = useState({
        college_id: "",
        year: "",
        registered_students: "",
        universal_contestants: "",
    });

    const onChange = (e) => {
        const newYearData = { ...yearData };
        newYearData[e.target.id] = e.target.value;
        setYearData(newYearData);
    };

    const postYear = (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append("college_id", parseInt(yearData.college_id));
        formData.append("year", parseInt(yearData.year));
        formData.append(
            "registered_students",
            parseInt(yearData.registered_students)
        );
        formData.append(
            "universal_contestants",
            parseInt(yearData.universal_contestants)
        );

        axios.post(url, formData).then((response) => {
            console.log(response.data);
        });
    };

    return (
        <div className="min-h-screen bg-gray-100">
            <Head title="Add Voting Category" />
            <div className="pt-40">
                <div className="mx-auto max-w-7xl sm:px-6 lg:px-8">
                    <div className="overflow-hidden bg-white shadow-sm sm:rounded-lg">
                        <div className="p-6 text-gray-900">
                            <form
                                onSubmit={postYear}
                                className="mt-6 space-y-6"
                            >
                                <InputLabel
                                    htmlFor="college_id"
                                    value="College Name"
                                />

                                <select
                                    name="college_id"
                                    id="college_id"
                                    onChange={onChange}
                                    value={yearData.college_id}
                                    className="block w-full mt-1"
                                >
                                    <option>Select College</option>
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

                                <InputLabel htmlFor="year" value="Year" />

                                <TextInput
                                    id="year"
                                    onChange={onChange}
                                    type="text"
                                    className="block w-full mt-1"
                                    autoComplete="year"
                                    name="year"
                                />

                                <InputError
                                    // message={category_name}
                                    className="mt-2"
                                />

                                <InputLabel
                                    htmlFor="registered_students"
                                    value="Registered Students"
                                />

                                <TextInput
                                    id="registered_students"
                                    onChange={onChange}
                                    type="text"
                                    className="block w-full mt-1"
                                    autoComplete="registered_students"
                                    name="registered_students"
                                />

                                <InputError
                                    // message={category_name}
                                    className="mt-2"
                                />

                                <InputLabel
                                    htmlFor="universal_contestants"
                                    value="Universal Contestants"
                                />

                                <TextInput
                                    id="universal_contestants"
                                    onChange={onChange}
                                    type="text"
                                    className="block w-full mt-1"
                                    autoComplete="universal_contestants"
                                    name="universal_contestants"
                                />

                                <InputError
                                    // message={category_name}
                                    className="mt-2"
                                />

                                <div className="flex justify-between">
                                    <PrimaryButton>add</PrimaryButton>

                                    <Link href={route("all.year")}>
                                        <SecondaryButton>
                                            back to years
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

export default PostYear;
