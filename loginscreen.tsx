import React, { useState } from 'react';
import { View, TextInput, Button, Alert, StyleSheet } from 'react-native';
import { NativeModules } from 'react-native';

const { LoginModule } = NativeModules;

const LoginScreen: React.FC = () => {
  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');

  const handleLogin = () => {
    if (username && password) {
      LoginModule.login(
        username,
        password,
        (error: string, successMessage: string) => {
          if (error) {
            Alert.alert('Login Error', error);
          } else {
            Alert.alert('Login Success', successMessage);
            // Lanjutkan ke halaman berikutnya jika diperlukan
          }
        }
      );
    } else {
      Alert.alert('Input Error', 'Please enter both username and password.');
    }
  };

  return (
    <View style={styles.container}>
      <TextInput
        placeholder="Username"
        value={username}
        onChangeText={setUsername}
        style={styles.input}
      />
      <TextInput
        placeholder="Password"
        value={password}
        onChangeText={setPassword}
        secureTextEntry={true}
        style={styles.input}
      />
      <Button title="Login" onPress={handleLogin} />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    padding: 20,
  },
  input: {
    height: 40,
    borderColor: 'gray',
    borderWidth: 1,
    marginBottom: 10,
    paddingHorizontal: 10,
  },
});

export default LoginScreen;
