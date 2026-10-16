import { DarkTheme, DefaultTheme, ThemeProvider } from 'expo-router';
import * as SplashScreen from 'expo-splash-screen';
import { useColorScheme } from 'react-native';

import { AnimatedSplashOverlay } from '@/components/animated-icon';
import AppTabs from '@/components/app-tabs';
import HomeScreen from "@/app/index";
import {useState} from "react";
import Counter from "@/app/Pages/Counter";
import GameCard from "@/app/Pages/GameCard";
import Comp1 from "@/app/Pages/comp1";

SplashScreen.preventAutoHideAsync();

export default function TabLayout() {
  const colorScheme = useColorScheme();
  const [show, setShow] = useState(true);
  return (
    <ThemeProvider value={colorScheme === 'dark' ? DarkTheme : DefaultTheme}>
      {show ? <Counter/> : <Comp1/>}
      <button onClick={ () => setShow(!show)} > Botón </button>
    </ThemeProvider>
  );
}
