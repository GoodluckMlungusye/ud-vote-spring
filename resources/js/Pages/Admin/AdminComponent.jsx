import React from "react";
import convertTime from "../Utilities/convertTime";
import useFetch from "../Utilities/useFetch";

function AdminComponent() {
    const {
        data: adminData,
        isLoading,
        errorMessage,
    } = useFetch("http://127.0.0.1:8000/api/admin");

    return (
        <div>
            {isLoading && <div>Loading Data...</div>}
            {errorMessage && <div>{errorMessage}</div>}
            {adminData && (
                <table className="w-full table-auto">
                    <thead className="font-semibold">
                        <tr>
                            <td>Serial No.</td>
                            <td>Name</td>
                            <td>Email</td>
                            <td>Created At</td>
                        </tr>
                    </thead>
                    <tbody>
                        {adminData.admin.map((item) => (
                            <tr key={item.id}>
                                <td>{item.id}</td>
                                <td>{item.name}</td>
                                <td>{item.email}</td>
                                <td>{convertTime(item.created_at)}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}

export default AdminComponent;
