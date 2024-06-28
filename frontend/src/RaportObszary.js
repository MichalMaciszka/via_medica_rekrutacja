import {useEffect, useState} from "react";
import axios from 'axios';


const RaportObszary = () => {
    const [data, setData] = useState([]);

    useEffect(() => {
        axios.get("http://localhost:8080/api/obszar")
            .then(res => {setData(res.data)})
            .catch(err => {console.log(err)});
    }, []);

    return (
        <div id={"container"}>
            <h3>Raport o obszarach</h3>
            <table border={1}>
                <thead>
                <tr>
                    <th>Obszar ID</th>
                    <th>Nazwa obszaru</th>
                    <th>Serwisant ID</th>
                    <th>Nazwisko serwisanta</th>
                </tr>
                </thead>
                <tbody>
                {data?.map((item, index) => (
                    <tr key={index}>
                        <td>{item.obszarId}</td>
                        <td>{item.obszarName}</td>
                        <td>{item.serwisantId}</td>
                        <td>{item.serwisantName}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    )
}
export default RaportObszary;
