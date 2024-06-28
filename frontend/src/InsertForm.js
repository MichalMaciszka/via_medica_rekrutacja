import { useEffect, useState } from 'react';
import axios from 'axios';
import {useNavigate} from "react-router-dom";

const InsertForm = () => {
    const navigate = useNavigate();
    const [serwisanci, setSerwisanci] = useState([]);
    const [obszary, setObszary] = useState([]);
    const [dzialanie, setDzialanie] = useState({
        identyfikatorSerwisanta: 0,
        identyfikatorObszaru: 0,
        opisDzialania: '',
        planowanyCzas: ''
    });

    useEffect(() => {
        // Fetch serwisanci
        axios.get('http://localhost:8080/api/serwisant')
            .then(response => {
                setSerwisanci(response.data);
                console.log(response.data);
            })
            .catch(error => {
                console.error("Błąd podczas wczytywania serwisantów", error);
            });

        // Fetch obszary
        axios.get('http://localhost:8080/api/obszar')
            .then(response => {
                setObszary(response.data);
                console.log(response.data);
            })
            .catch(error => {
                console.error("Błąd podczas wczytywania obszarów", error);
            });
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setDzialanie(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        // Submit the form data
        console.log("submit");
        console.log(dzialanie);
        axios.post('http://localhost:8080/api/dzialanie', dzialanie)
            .then(response => {
                console.log('Dzialanie dodane:', response.data);
                navigate("/");
            })
            .catch(error => {
                console.error('Wystapil blad przy dodawaniu', error);
            });
    };

    return (
        <div id="container">
            <h3>Dodaj działanie do obszaru</h3>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="identyfikatorSerwisanta">Serwisant:</label>
                    <select name="identyfikatorSerwisanta" value={dzialanie.identyfikatorSerwisanta} onChange={handleChange} required>
                        <option value="">Wybierz serwisanta</option>
                        {serwisanci.map(serwisant => (
                            <option key={serwisant.identyfikator} value={serwisant.identyfikator}>{serwisant.nazwisko}</option>
                        ))}
                    </select>
                </div>
                <div>
                    <label htmlFor="identyfikatorObszaru">Obszar:</label>
                    <select name="identyfikatorObszaru" value={dzialanie.identyfikatorObszaru} onChange={handleChange} required>
                        <option value="">Wybierz obszar</option>
                        {obszary.map(obszar => (
                            <option key={obszar.obszarId} value={obszar.obszarId}>{obszar.obszarName}</option>
                        ))}
                    </select>
                </div>
                <div>
                    <label htmlFor="opisDzialania">Opis Działania:</label>
                    <textarea name="opisDzialania" value={dzialanie.opisDzialania} onChange={handleChange} required></textarea>
                </div>
                <div>
                    <label htmlFor="planowanyCzas">Planowany Czas (minuty):</label>
                    <input type="number" name="planowanyCzas" value={dzialanie.planowanyCzas} onChange={handleChange} required />
                </div>
                <button type="submit">Dodaj Działanie</button>
            </form>
        </div>
    );
};

export default InsertForm;
