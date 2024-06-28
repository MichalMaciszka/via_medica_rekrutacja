import './App.css';
import ObszaryZSerwisantami from "./Obszary";
import {Route, Routes} from "react-router";
import EditForm from "./EditForm";
import InsertForm from "./InsertForm";
import RaportObszary from "./RaportObszary";
import RaportDzialania from "./RaportDzialania";
import Menu from "./Menu";

function App() {
  return (
      <div className="App">
          <header className="App-header">
              <Menu />
              <Routes>
                  <Route path="/" element={<ObszaryZSerwisantami/>}/>
                  <Route path="edytujSerwisanta" element={<EditForm/>}/>
                  <Route path="dodajDzialanie" element={<InsertForm/>}/>
                  <Route path="raportObszary" element={<RaportObszary/>}/>
                  <Route path="raportDzialania" element={<RaportDzialania/>}/>
              </Routes>
          </header>
      </div>
);
}

export default App;
