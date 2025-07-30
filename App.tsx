// import React, { useState } from 'react';
// import { View, TextInput, Button, Alert } from 'react-native';
// import { NativeModules } from 'react-native';

// const { EversafeModule } = NativeModules;

// const LoginScreen: React.FC = () => {
//   const [username, setUsername] = useState<string>('');
//   const [password, setPassword] = useState<string>('');

//   const handleLogin = () => {
//     if (username && password) {
//       const jsonString = JSON.stringify({
//         user_name: username,
//         user_password: password,
//       });

//       console.log('Request JSON:', jsonString);

//       // Kirim data ke native untuk dienkripsi
//       EversafeModule.encrypt(jsonString, (error: string, encryptedRequestJson: string) => {
//         console.log('jsonString:', jsonString);
//         if (error) {
//           Alert.alert('Login Error', error);
//         } else {
//           console.log('Request to Backend:', encryptedRequestJson);

//           // fetch('http://103.96.146.239:9007/eversafe/user_login_eversafe', { // IP VPS
//           fetch('https://api.everspin.my.id/eversafe/user_login_eversafe', { // Domain VPS
//             method: 'POST',
//             headers: {
//               'Content-Type': 'application/json',
//             },
//             body: encryptedRequestJson,
//           })
//             .then(response => {
//               console.log('Response from Backend:', response);
//               if (!response.ok) {
//                 throw new Error(`HTTP error! status: ${response.status}`);
//               }
//               return response.json();
//             })
//             .then(data => {
//               console.log('Response Data:', data);
//               if (data.result === true) {
//                 if (data.payload) {
//                   console.log('Payload exists');
//                   handleDecrypt(data.payload);
//                 } else {
//                   Alert.alert('Login Success', 'Login berhasil tetapi tidak ada payload terenkripsi yang diterima.');
//                 }
//               } else {
//                 Alert.alert('Login Failed', 'Login gagal. Silakan periksa kredensial Anda.');
//               }
//             })
//             .catch((error: Error) => {
//               console.error('Error:', error);
//               Alert.alert('Network Error', 'Terjadi kesalahan saat mencoba terhubung ke server.');
//             });
//         }
//       });
//     } else {
//       Alert.alert('Input Error', 'Silakan masukkan username dan password.');
//     }
//   };

//   const handleDecrypt = (encryptedPayload: string) => {
//     console.log('Decrypt payload:', encryptedPayload);
//     EversafeModule.decrypt(encryptedPayload, (error: string, decryptedPayload: string) => {
//       console.log('Decrypt run');
//       console.log('Decrypted payload:', decryptedPayload);
//       if (error) {
//         console.error('Decryption Error:', error);
//         Alert.alert('Decryption Error', error);
//       } else {
//         console.log('Decrypted Payload:', decryptedPayload);
//         Alert.alert('Decryption Success', decryptedPayload);
//       }
//     });
//   };

//   return (
//     <View style={{ padding: 20 }}>
//       <TextInput
//         placeholder="Username"
//         value={username}
//         onChangeText={setUsername}
//         style={{ height: 40, borderColor: 'gray', borderWidth: 1, marginBottom: 10 }}
//       />
//       <TextInput
//         placeholder="Password"
//         value={password}
//         onChangeText={setPassword}
//         secureTextEntry={true}
//         style={{ height: 40, borderColor: 'gray', borderWidth: 1, marginBottom: 10 }}
//       />
//       <Button title="Login" onPress={handleLogin} />
//     </View>
//   );
// };

// export default LoginScreen;

import React, { useState } from 'react';
import { View, TextInput, Button, Alert } from 'react-native';
import { NativeModules } from 'react-native';

const { EversafeModule } = NativeModules;

const LoginScreen: React.FC = () => {
  const [username, setUsername] = useState<string>('user1@test.com');
  const [password, setPassword] = useState<string>('123456');

  const handleLogin = () => {
    if (username && password) {
      const jsonString = JSON.stringify({
        user_name: username,
        user_password: password,
      });

      console.log('Request JSON:', jsonString);

      EversafeModule.encrypt(jsonString, (error: string, encryptedRequestJson: string) => {
        console.log('jsonString:', jsonString);
        if (error) {
          Alert.alert('Login Error', error);
        } else {
          console.log('Request to Backend:', encryptedRequestJson);

          // fetch('http://103.96.146.239:9007/eversafe/user_login_eversafe', { // IP VPS
          fetch('https://api.everspin.my.id/eversafe/user_login_eversafe', { // Domain VPS
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: encryptedRequestJson,
          })
            .then(response => {
              console.log('Response from Backend:', response);
              if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
              }
              return response.json();
            })
            .then(data => {
              console.log('Response Data:', data);
              if (data.result === true) {
                if (data.payload) {
                  console.log('Payload exists');
                  handleDecrypt(data.payload);
                } else {
                  Alert.alert('Login Success', 'Login berhasil tetapi tidak ada payload terenkripsi yang diterima.');
                }
              } else {
                Alert.alert('Login Failed', 'Login gagal. Silakan periksa kredensial Anda.');
              }
            })
            .catch((error: Error) => {
              console.error('Error:', error);
              Alert.alert('Network Error', 'Terjadi kesalahan saat mencoba terhubung ke server.');
            });
        }
      });
    } else {
      Alert.alert('Input Error', 'Silakan masukkan username dan password.');
    }
  };

  const handleDecrypt = (encryptedPayload: string) => {
    console.log('Decrypt payload:', encryptedPayload);
    EversafeModule.decrypt(encryptedPayload, (error: string, decryptedPayload: string) => {
      console.log('Decrypt run');
      console.log('Decrypted payload:', decryptedPayload);
      if (error) {
        console.error('Decryption Error:', error);
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

