import React, { useState } from 'react';
import { View, TextInput, Button, Alert } from 'react-native';
import { NativeModules } from 'react-native';

const { LoginModule } = NativeModules;

const LoginScreen = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = () => {
    if (username && password) {
      LoginModule.login(username, password, (error, verificationTokenAsBase64) => {
        if (error) {
          Alert.alert('Login Error', error);
        } else {
          console.log('Request to Backend:', verificationTokenAsBase64);

          fetch('http://10.8.12.99:9002/everspin/verify_token', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({
              token: verificationTokenAsBase64
            }),
          })
          .then(response => response.json())
          .then(data => {
            console.log('Response from Backend:', data);
            Alert.alert('Success', 'Token verified successfully');
          })
          .catch(error => {
            console.error('Error:', error);
            Alert.alert('Error', 'Something went wrong');
          });
        }
      });
    } else {
      Alert.alert('Error', 'Please enter both username and password');
    }
  };

  return (
    <View style={{ padding: 20 }}>
      <TextInput
        placeholder="Username"
        value={username}
        onChangeText={setUsername}
        style={{ height: 40, borderColor: 'gray', borderWidth: 1, marginBottom: 10 }}
      />
      <TextInput
        placeholder="Password"
        value={password}
        onChangeText={setPassword}
        secureTextEntry={true}
        style={{ height: 40, borderColor: 'gray', borderWidth: 1, marginBottom: 10 }}
      />
      <Button title="Login" onPress={handleLogin} />
    </View>
  );
};

export default LoginScreen;
