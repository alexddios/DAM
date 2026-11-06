import { Stack } from 'expo-router';
import { StatusBar } from 'expo-status-bar';
import {LugarProvider} from "../context/lugares";
import {colores} from "../constants/colores";

export default function Layout(){
  return (
      <LugarProvider>
          <StatusBar style="light" />
          <Stack
              screenOptions={{
                  headerStyle: { backgroundColor: colores.principal },
                  headerTintColor: colores.sobrePrincipal,
                  headerTitleStyle: { fontWeight: '700' },
                  headerShadowVisible: false,
                  contentStyle: { backgroundColor: colores.fondo },
              }}
          >
              <Stack.Screen name="index" options={{ title: 'MyPlaces' }} />
              <Stack.Screen name="detall/[id]" options={{ title: 'Detalle' }} />
              <Stack.Screen name="nuevo-lugar" options={{title: 'Nuevo lugar'}}/>
              <Stack.Screen name= "descubrir" options={{title: 'Descubrir'}}/>
          </Stack>
      </LugarProvider>

  );
}
