import {Pressable, StyleSheet, Text, View} from "react-native";
import {router} from "expo-router";
import {colores} from "../constants/colores";

export default function TarjetaLugar({lugar,eliminarLugar}){
   return(
       <View style={styles.tarjeta}>
           <View style={styles.chip}>
               <Text style={styles.textoChip}>{lugar.categoria}</Text>
           </View>
           <Text style={styles.nombre}>{lugar.nombre}</Text>
           {/* Las dos acciones en la misma fila: la principal rellena, la de borrar con borde. */}
           <View style={styles.acciones}>
               {/* En web, <Link asChild> pierde el style en forma de función,
                  así que navegamos con router.push para conservar el efecto al pulsar. */}
               <Pressable
                   accessibilityRole="button"
                   accessibilityLabel={`Ver ${lugar.nombre}`}
                   onPress={() =>
                       router.push({ pathname: '/detall/[id]', params: { id: lugar.id } })
                   }
                   style={({ pressed }) => [styles.boton, pressed && styles.pulsado]}
               >
                   <Text style={styles.textoBoton}>Ver detalle</Text>
               </Pressable>
               <Pressable
                   accessibilityRole="button"
                   accessibilityLabel={`Eliminar ${lugar.nombre}`}
                   onPress={() => eliminarLugar(lugar.id)}
                   style={({ pressed }) => [styles.botonEliminar, pressed && styles.pulsado]}
               >
                   <Text style={styles.textoEliminar}>Eliminar</Text>
               </Pressable>
           </View>
       </View>
   )
}
const styles = StyleSheet.create({
    tarjeta: {
        padding: 18,
        gap: 10,
        backgroundColor: colores.superficie,
        borderRadius: 12,
        borderLeftWidth: 5,
        borderLeftColor: colores.principal,
        shadowColor: '#000',
        shadowOpacity: 0.06,
        shadowRadius: 8,
        shadowOffset: { width: 0, height: 2 },
        elevation: 2,
    },
    chip: {
        alignSelf: 'flex-start',
        backgroundColor: colores.principalSuave,
        borderRadius: 999,
        paddingHorizontal: 10,
        paddingVertical: 3,
    },
    textoChip: { fontSize: 12, fontWeight: '700', color: colores.principal },
    nombre: { fontSize: 20, fontWeight: '700', color: colores.texto },
    acciones: { flexDirection: 'row', gap: 10, marginTop: 4 },
    boton: {
        flex: 1,
        backgroundColor: colores.principal,
        borderRadius: 999,
        minHeight: 44,
        justifyContent: 'center',
        alignItems: 'center',
    },
    pulsado: { opacity: 0.7 },
    textoBoton: { color: colores.sobrePrincipal, fontSize: 15, fontWeight: '600' },
    botonEliminar: {
        borderWidth: 1.5,
        borderColor: colores.peligro,
        borderRadius: 999,
        paddingHorizontal: 18,
        minHeight: 44,
        justifyContent: 'center',
        alignItems: 'center',
    },
    textoEliminar: { color: colores.peligro, fontSize: 15, fontWeight: '600' },
});
