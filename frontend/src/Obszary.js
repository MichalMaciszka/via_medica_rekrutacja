import {useEffect, useState} from "react";
import axios from 'axios';
import {Link} from "react-router-dom";

const ObszaryZSerwisantami = () => {
    const [data, setData] = useState([]);
    const [message, setMessage] = useState("");

    useEffect(() => {
        axios.get("http://localhost:8080/api/obszar")
            .then(res => {setData(res.data)})
            .catch(err => {console.log(err)});
    }, []);

    function sendEmails() {
        axios.get("http://localhost:8080/api/sendEmails")
            .then(_ => {setMessage("Wysłano pomyślnie")})
            .catch(_ => setMessage("Błąd wysyłania"));
    }

    return (
        <div id={"container"}>
            <h3>Lista obszarów z przypisanymi serwisantami</h3>
            <button onClick={sendEmails}>Wyślij działania do serwisantów</button>
            <p>{message}</p>
            <br/>
            <br/>
            <table border={1}>
                <thead>
                <tr>
                    <th>Obszar ID</th>
                    <th>Nazwa obszaru</th>
                    <th>Serwisant ID</th>
                    <th>Nazwisko serwisanta</th>
                    <th>Edytuj serwisanta</th>
                </tr>
                </thead>
                <tbody>
                {data?.map((item, index) => (
                    <tr key={index}>
                        <td>{item.obszarId}</td>
                        <td>{item.obszarName}</td>
                        <td>{item.serwisantId}</td>
                        <td>{item.serwisantName}</td>
                        <td><Link to={"edytujSerwisanta?id=" + item.serwisantId.toString()}>Edytuj</Link></td>
                    </tr>
                ))}
                </tbody>
            </table>
            <br/>
        </div>
    )
}
export default ObszaryZSerwisantami;
