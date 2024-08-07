import React, { useState } from 'react';
import { View, TextInput, Button, Alert } from 'react-native';
import { NativeModules } from 'react-native';

const { LoginModule } = NativeModules;

const LoginScreen = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = () => {
    if (username && password) {
      LoginModule.login(username, password, (error, successMessage) => {
        if (error) {
          Alert.alert('Login Error', error);
        } else {
          Alert.alert('Login Success', successMessage);
          // Lanjutkan ke halaman berikutnya jika diperlukan
        }
      });
    } else {
      Alert.alert('Input Error', 'Please enter both username and password.');
    }
  };

  return (
    <View>
      <TextInput
        placeholder="Username"
        value={username}
        onChangeText={setUsername}
        style={{ height: 40, borderColor: 'gray', borderWidth: 1 }}
      />
      <TextInput
        placeholder="Password"
        value={password}
        onChangeText={setPassword}
        secureTextEntry={true}
        style={{ height: 40, borderColor: 'gray', borderWidth: 1 }}
      />
      <Button title="Login" onPress={handleLogin} />
    </View>
  );
};

export default LoginScreen;