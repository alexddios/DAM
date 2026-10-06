import type { ReactNode } from 'react';
import { Image,View, StyleSheet } from 'react-native';

import { ThemedText } from './themed-text';
import { ThemedView } from './themed-view';

import { Spacing } from '@/constants/theme';

type HintRowProps = {
  numero: number;
  title?: string;
  hint?: ReactNode;
  image?: any;
};

export function HintRow({
                          numero,
                          title = 'Try editing',
                          hint = 'app/index.tsx',
                          image,
                        }: HintRowProps) {
  return (
      <View style={styles.stepRow}>
        <View style={styles.textContainer}>
          <ThemedText type="small">
            {numero}. {title}
          </ThemedText>

          <ThemedView type="backgroundSelected" style={styles.codeSnippet}>
            <ThemedText themeColor="textSecondary">{hint}</ThemedText>
          </ThemedView>
        </View>

        {image && (
            <Image
                source={image}
                style={styles.image}
            />
        )}
      </View>
  );
}

const styles = StyleSheet.create({
  stepRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  textContainer: {
    flex: 1,
  },
  codeSnippet: {
    borderRadius: Spacing.two,
    paddingVertical: Spacing.half,
    paddingHorizontal: Spacing.two,
  },
  image: {
    width: 60,
    height: 60,
    marginLeft: 15,
  },
});
