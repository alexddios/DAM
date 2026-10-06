import { useState } from 'react';
import { Button, Text, View } from 'react-native';

export default function Counter() {
    const [count, setCount] = useState(0);

    return (
        <View>
            <Text>Clics: {count}</Text>

            <Button
                title="Clic aquí"
                onPress={() => setCount(count + 1)}
            />
        </View>
    );
}