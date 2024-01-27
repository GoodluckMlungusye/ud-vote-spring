import React from "react";
import convertTime from "../Utilities/convertTime";
import useFetch from "../Utilities/useFetch";
import AuthenticatedLayout from "@/Layouts/AuthenticatedLayout";
import { Head } from "@inertiajs/react";
import PrimaryButton from "@/Components/PrimaryButton";
import { Link } from "@inertiajs/react";


const Colleges = (props) => {

    const {
        data: collegesData,
        isLoading,
        errorMessage,
    } = useFetch("http://127.0.0.1:8000/api/colleges");



    return (
        <AuthenticatedLayout
            auth={props.auth}
            errors={props.errors}
            header={
                <div className="flex items-center justify-between">
                    <h2 className="text-xl font-semibold leading-tight text-gray-800">
                        Colleges
                    </h2>

                    <Link href={route("post.college")}>
                        <PrimaryButton>Add New College</PrimaryButton>
                    </Link>
                </div>
            }
        >
            <Head title="Colleges" />

            <div className="py-12">
                <div className="mx-auto max-w-7xl sm:px-6 lg:px-8">
                    <div className="overflow-hidden bg-white shadow-sm sm:rounded-lg">
                        <div className="p-6 text-gray-900">
                            {isLoading && <div>Loading Data...</div>}
                            {errorMessage && <div>{errorMessage}</div>}
                            {collegesData && (
                                <table className="w-full table-auto">
                                    <thead className="font-semibold">
                                        <tr>
                                            <td>Serial No.</td>
                                            <td>College name</td>
                                            <td>Image</td>
                                            <td>Created at</td>
                                            <td>Actions</td>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {collegesData.colleges.map(
                                            (item) => (
                                                <tr key={item.id}>
                                                    <td>{item.id}</td>
                                                    <td>
                                                        {item.college_name}
                                                    </td>
                                                    <td>
                                                        <img
                                                            src={`http://127.0.0.1:8000/storage/${item.college_image}`}
                                                            alt={`${item.college_name}'s image`}
                                                            className="w-16 h-16"
                                                        />
                                                    </td>
                                                    <td>
                                                        {convertTime(
                                                            item.created_at
                                                        )}
                                                    </td>

                                                    <td>
                                                        <Link
                                                            href={`/college/edit/${item.id}`}
                                                        >
                                                            <svg
                                                                xmlns="http://www.w3.org/2000/svg"
                                                                fill="none"
                                                                viewBox="0 0 24 24"
                                                                stroke-width="1.5"
                                                                stroke="currentColor"
                                                                class="w-6 h-6"
                                                                className="w-10 h-10 text-red-600"
                                                            >
                                                                <path
                                                                    stroke-linecap="round"
                                                                    stroke-linejoin="round"
                                                                    d="M9.594 3.94c.09-.542.56-.94 1.11-.94h2.593c.55 0 1.02.398 1.11.94l.213 1.281c.063.374.313.686.645.87.074.04.147.083.22.127.324.196.72.257 1.075.124l1.217-.456a1.125 1.125 0 011.37.49l1.296 2.247a1.125 1.125 0 01-.26 1.431l-1.003.827c-.293.24-.438.613-.431.992a6.759 6.759 0 010 .255c-.007.378.138.75.43.99l1.005.828c.424.35.534.954.26 1.43l-1.298 2.247a1.125 1.125 0 01-1.369.491l-1.217-.456c-.355-.133-.75-.072-1.076.124a6.57 6.57 0 01-.22.128c-.331.183-.581.495-.644.869l-.213 1.28c-.09.543-.56.941-1.11.941h-2.594c-.55 0-1.02-.398-1.11-.94l-.213-1.281c-.062-.374-.312-.686-.644-.87a6.52 6.52 0 01-.22-.127c-.325-.196-.72-.257-1.076-.124l-1.217.456a1.125 1.125 0 01-1.369-.49l-1.297-2.247a1.125 1.125 0 01.26-1.431l1.004-.827c.292-.24.437-.613.43-.992a6.932 6.932 0 010-.255c.007-.378-.138-.75-.43-.99l-1.004-.828a1.125 1.125 0 01-.26-1.43l1.297-2.247a1.125 1.125 0 011.37-.491l1.216.456c.356.133.751.072 1.076-.124.072-.044.146-.087.22-.128.332-.183.582-.495.644-.869l.214-1.281z"
                                                                />
                                                                <path
                                                                    stroke-linecap="round"
                                                                    stroke-linejoin="round"
                                                                    d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"
                                                                />
                                                            </svg>
                                                        </Link>
                                                    </td>
                                                </tr>
                                            )
                                        )}
                                    </tbody>
                                </table>
                            )}
                        </div>
                    </div>
                </div>
            </div>
        </AuthenticatedLayout>
    );



}

export default Colleges
