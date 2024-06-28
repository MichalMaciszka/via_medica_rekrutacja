import {useEffect, useState} from "react";
import axios from 'axios';

const RaportDzialania = () => {
    const [serwisanci, setSerwisanci] = useState([]);
    const [selectedSerwisant, setSelectedSerwisant] = useState('');
    const [raport, setRaport] = useState([]);

    useEffect(() => {
        axios.get("http://localhost:8080/api/serwisant")
            .then(res => {
                setSerwisanci(res.data)
            })
            .catch(err => {
                console.log(err)
            });
    }, []);
    console.log(serwisanci);
    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(`Wybrany serwisant: ${selectedSerwisant}`);
        axios.get("http://localhost:8080/api/serwisant/" + selectedSerwisant.toString() + "/dzialania")
            .then(res => {
                setRaport(res.data);
                console.log(res.data);
            })
            .catch(err => console.log(err));
    };

    return (
        <div id={"container"}>
            <h3>Raport o działaniach</h3>
            <form onSubmit={handleSubmit}>
                <label htmlFor="serwisant">Wybierz serwisanta:</label>
                <select
                    id="serwisant"
                    value={selectedSerwisant}
                    onChange={(e) => setSelectedSerwisant(e.target.value)}
                >
                    <option value="" disabled>Wybierz serwisanta</option>
                    {serwisanci?.map((serwisant) => (
                        <option key={serwisant.identyfikator} value={serwisant.identyfikator}>
                            {serwisant.nazwisko} ({serwisant.identyfikator})
                        </option>
                    ))}
                </select>
                <br/>
                <button type="submit">Generuj</button>
            </form>
            <div id="tabela">
                {raport.length > 0 ? (
                    <table border={1}>
                        <thead>
                        <tr>
                            <th>Identyfikator</th>
                            <th>Identyfikator Serwisanta</th>
                            <th>Identyfikator Obszaru</th>
                            <th>Opis Działania</th>
                            <th>Planowany Czas</th>
                        </tr>
                        </thead>
                        <tbody>
                        {raport.map((dzialanie) => (
                            <tr key={dzialanie.identyfikator}>
                                <td>{dzialanie.identyfikator}</td>
                                <td>{dzialanie.identyfikatorSerwisanta}</td>
                                <td>{dzialanie.identyfikatorObszaru}</td>
                                <td>{dzialanie.opisDzialania}</td>
                                <td>{dzialanie.planowanyCzas}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                ) : (
                    <p>Brak danych do wyświetlenia</p>
                )}
            </div>
        </div>
    )
}
export default RaportDzialania;
