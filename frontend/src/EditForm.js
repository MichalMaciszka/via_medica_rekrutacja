import {useEffect, useState} from "react";
import axios from 'axios';
import {useSearchParams, useNavigate} from "react-router-dom";

const EditForm = () => {
    const [searchParams, setSearchParams] = useSearchParams();
    const navigate = useNavigate();
    let id = searchParams.get("id");
    const [details, setDetails] = useState({
        identyfikator: '',
        nazwisko: '',
        aktywny: false,
        email: ''
    });

    useEffect(() => {
        axios.get("http://localhost:8080/api/serwisant/" + id.toString())
            .then(res => {setDetails(res.data)})
            .catch(err => {
                console.log(err);
                navigate("/");
            });
    }, []);

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setDetails(prevDetails => ({
            ...prevDetails,
            [name]: type === 'checkbox' ? checked : value
        }));
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log("submit");
        axios.put("http://localhost:8080/api/serwisant/" + id.toString(), details)
            .then(_ => navigate("/"))
            .catch(e => console.error(e));
    }

    return (
        <div id={"container"}>
            <h3>Edycja serwisanta o ID {id}</h3>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>
                        Identyfikator:
                        <input type="text" value={details.identyfikator} disabled/>
                    </label>
                </div>
                <div>
                    <label>
                        Nazwisko:
                        <input
                            type="text"
                            name="nazwisko"
                            value={details.nazwisko}
                            onChange={handleChange}
                        />
                    </label>
                </div>
                <div>
                    <label>
                        Aktywny:
                        <input
                            type="checkbox"
                            name="aktywny"
                            checked={details.aktywny}
                            onChange={handleChange}
                        />
                    </label>
                </div>
                <div>
                    <label>
                        Email:
                        <input
                            type="email"
                            name="email"
                            value={details.email}
                            onChange={handleChange}
                        />
                    </label>
                </div>
                <button type="submit">Zapisz</button>
            </form>
        </div>
    )
}
export default EditForm;
