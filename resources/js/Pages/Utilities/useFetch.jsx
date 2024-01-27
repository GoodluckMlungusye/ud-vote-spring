import { useState, useEffect } from "react";
import axios from "axios";

function useFetch(url) {

    const [data, setData] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const [errorMessage, setErrorMessage] = useState(null);

    useEffect(() => {
        axios
            .get(url)
            .then((response) => {
                setIsLoading(false);
                setData(response.data);
            })
            .catch((error) => {
                setIsLoading(false);
                setErrorMessage(error.message);
                setData(null);
            });
    }, [url]);

   return {data, isLoading, errorMessage}
}


export default useFetch;


