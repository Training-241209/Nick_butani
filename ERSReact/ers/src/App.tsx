import { Routes, Route } from 'react-router-dom';
import Homepage from './app/home/page';
import Login from './app/login/page';
import Signup from './app/signup/page';
import Dashboard from './app/dashboard/page';
import Reimburse from './app/reimburse/page';
import AuthLayout from './Authlayout';
import Users from './app/users/page';

function App() {
  return (
    <Routes>
      {/* Public routes */}
      <Route path="/" element={<Homepage />} />
      <Route path="/login" element={<Login />} />
      <Route path="/signup" element={<Signup />} />
      
      
      {/* Protected routes */}
      <Route element={<AuthLayout />}>
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/reimburse" element={<Reimburse />} />
        <Route path="/users" element={<Users />} />
      </Route>
    </Routes>
  );
}

export default App;