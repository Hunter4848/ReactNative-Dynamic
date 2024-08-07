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
