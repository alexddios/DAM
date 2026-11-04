import {createContext, useContext, useEffect, useState} from 'react';
import {llocs} from "../data/llocs";
import AsyncStorage from "@react-native-async-storage/async-storage";
const LugaresContext = createContext(/** @type {any} */ (null));
/** @param {{ children: import('react').ReactNode }} props */
export function LugarProvider({children}) {

    const [lugares, setLugares] = useState(llocs);
    const [cargado, setCargado] = useState(false);

    useEffect(()=>{
        async function cargar(){
        try {
            const text = await AsyncStorage.getItem('llocs');
            setLugares(text ? JSON.parse(text) : llocs)
        }catch (error){
            console.log(error)
        }finally {
            setCargado(true);
        }
        }
        cargar();
        },[]);
    useEffect(() => {
        if (!cargado) return;
        async function guardar(){
            try {
                await AsyncStorage.setItem('llocs',JSON.stringify(lugares))
            }catch (e) {
                console.log(e)
            }
        }
        guardar();
    }, [lugares,cargado]);

    /** @param {{ nombre: string, descripcion: string, categoria: string }} datos */
    function añadirLugar(datos) {
        const nuevo = {...datos, id: Date.now().toString()}
        setLugares((anteriores) => [...anteriores,nuevo]);
    }

    /**
     * @param {string} id
     */
    function eliminarLugar(id){
        setLugares((anteriores) => anteriores.filter((lugar) => lugar.id !== id))
    }
    return (
        <LugaresContext.Provider value={{ lugares, añadirLugar,eliminarLugar }}>
            {children}
        </LugaresContext.Provider>
    );


}
export function useLugares() {
    return useContext(LugaresContext);
}