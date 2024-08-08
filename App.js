/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React, { useState } from 'react';
import { View, TextInput, Button, Alert } from 'react-native';
import { NativeModules } from 'react-native';

const { LoginModule } = NativeModules;

const LoginScreen = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = () => {
    if (username && password) {
      LoginModule.login(username, password, (error, encryptedRequestJson) => {
        if (error) {
          Alert.alert('Login Error', error);
        } else {
          // Mengirim encryptedRequestJson ke backend
          const request = JSON.parse(encryptedRequestJson);
              console.log('Request to Backend:', request);

          fetch('http://10.8.12.37:9002/everspin/user_login_eversafe', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(request),
          })
          .then(response => {
                     // Log the entire response object
                     console.log('Response from Backend:', response);

                     // Log response status and headers
                     console.log('Response Status:', response.status);
                     console.log('Response Headers:', response.headers);

                     // Parse the JSON response
                     return response.json();
                   })
          .then(data => {
            // Assuming `data` contains an encryptedPayload
            console.log('Response Data:', data);
            console.log('Response Data:', data.encryptedPayload);

            if (data.encryptedPayload) {
              handleDecrypt(data.encryptedPayload);
            } else {
              Alert.alert('Error', 'No encrypted payload received');
            }
          })
          .catch((error) => {
            console.error('Error:', error);
            Alert.alert('Network Error', 'An error occurred while trying to connect to the server.');
          });
        }
      });
    } else {
      Alert.alert('Input Error', 'Please enter both username and password.');
    }
  };

  const handleDecrypt = (encryptedPayload) => {
    LoginModule.decrypt(encryptedPayload, (error, decryptedPayload) => {
      if (error) {
        console.error("Decryption Error:", error);
        Alert.alert('Decryption Error', error);
      } else {
        console.log('Decrypted Payload:', decryptedPayload);
        Alert.alert('Decryption Success', decryptedPayload);
      }
    });
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

/*
import React, { useState } from 'react';
import { SafeAreaView, Text, TextInput, Button, View, StyleSheet, useColorScheme, StatusBar } from 'react-native';
import {
  Colors,
} from 'react-native/Libraries/NewAppScreen';

// Komponen LoginScreen
const LoginScreen = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = () => {
    // Lakukan validasi login atau kirim data ke backend di sini
    console.log('Username:', username);
    console.log('Password:', password);
  };

  return (
    <View style={styles.loginContainer}>
      <Text style={styles.title}>Login</Text>
      <TextInput
        style={styles.input}
        placeholder="Username"
        value={username}
        onChangeText={setUsername}
      />
      <TextInput
        style={styles.input}
        placeholder="Password"
        value={password}
        onChangeText={setPassword}
        secureTextEntry
      />
      <Button title="Login" onPress={handleLogin} />
    </View>
  );
};

// Komponen utama App
const App = () => {
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
    flex: 1, // Tambahkan flex agar tampilan login memenuhi layar
  };

  return (
    <SafeAreaView style={backgroundStyle}>
      <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />
      <LoginScreen />
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  loginContainer: {
    flex: 1,
    justifyContent: 'center',
    padding: 16,
  },
  title: {
    fontSize: 24,
    textAlign: 'center',
    marginBottom: 24,
  },
  input: {
    height: 40,
    borderColor: 'gray',
    borderWidth: 1,
    marginBottom: 12,
    paddingHorizontal: 8,
  },
});

export default App;

*/
