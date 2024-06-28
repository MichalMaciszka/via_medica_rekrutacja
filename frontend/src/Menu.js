import { Link } from "react-router-dom";
import './Menu.css';

function Menu() {
    return (
        <nav className="Menu">
            <ul>
                <li><Link to="/">Obszary z serwisantami</Link></li>
                <li><Link to="/dodajDzialanie">Dodaj Działanie</Link></li>
                <li><Link to="/raportObszary">Raport Obszary</Link></li>
                <li><Link to="/raportDzialania">Raport Działania</Link></li>
            </ul>
        </nav>
    );
}

export default Menu;
