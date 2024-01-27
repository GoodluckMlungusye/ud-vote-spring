import React, { useState } from "react";
import InputError from "@/Components/InputError";
import InputLabel from "@/Components/InputLabel";
import PrimaryButton from "@/Components/PrimaryButton";
import DangerButton from "@/Components/DangerButton";
import TextInput from "@/Components/TextInput";
import { Head } from "@inertiajs/react";
import { Link } from "@inertiajs/react";
import SecondaryButton from "@/Components/SecondaryButton";


const EditYear = (props) => {

    const collegeData = props.college;
    const year = props.year;

    const updateUrl = `http://127.0.0.1:8000/api/year/update/${year.id}`;
    const deleteUrl = `http://127.0.0.1:8000/api/year/delete/${year.id}`;

    const [yearData, setYearData] = useState({

        college_id: props.year.college_id,
        year: props.year.year,
        registered_students: props.year.registered_students,
        universal_contestants: props.year.universal_contestants

    });

    const onChange = (e) => {
        const newYearData = { ...yearData };
        newYearData[e.target.id] = e.target.value;
        setYearData(newYearData);
    };


    const editYear = (e) => {
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

        axios
            .post(updateUrl, formData)
            .then((response) => {
                console.log(response.data);
            });
    }

    const deleteYear = () => {
        axios.delete(deleteUrl).then((response) => {
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
                                onSubmit={editYear}
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
                                    {year && <option>{year.college.college_name}</option>}
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
                                    value={yearData.year}
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
                                    value={yearData.registered_students}
                                />

                                <InputError
                                    // message={category_name}
                                    className="mt-2"
                                />

                                <InputLabel
                                    htmlFor="universal_contestants"
                                    value="Voted Students"
                                />

                                <TextInput
                                    id="universal_contestants"
                                    onChange={onChange}
                                    type="text"
                                    className="block w-full mt-1"
                                    autoComplete="universal_contestants"
                                    name="universal_contestants"
                                    value={yearData.universal_contestants}
                                />

                                <InputError
                                    // message={category_name}
                                    className="mt-2"
                                />



                                <div className="flex justify-between">
                                    <div>
                                    <PrimaryButton className="mr-4">update</PrimaryButton>

                                    <Link href={route("all.year")}>
                                        <SecondaryButton>
                                            back to years
                                        </SecondaryButton>
                                    </Link>

                                    </div>
                                    <Link href={route('all.year')}>
                                        <DangerButton onClick={deleteYear}>delete year</DangerButton>
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

export default EditYear;
