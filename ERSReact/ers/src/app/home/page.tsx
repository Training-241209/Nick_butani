

import { Button } from "@/components/ui/button";
import { useNavigate } from 'react-router-dom';

export default function Homepage(){
    const navigate  = useNavigate();
    return (
        <div className="min-h-screen 0 flex items-center justify-center p-6">
        <div className="flex flex-col items-center gap-8 p-8 rounded-lg ">
            <h1 className="text-4xl font-extrabold text-gray-800">Employee Management System</h1>
            <p className="text-base text-gray-600 text-center">
            Welcome to the Employee Management System. Please login to access your dashboard.
            </p>
            <div className="flex gap-4">
            <Button className="px-6 py-3 bg-blue-600 text-white font-medium text-sm uppercase rounded-lg hover:bg-blue-700 transition duration-300" onClick={() => navigate('/login?role=EMPLOYEE')} >
                Employee
            </Button>
            <Button onClick={() => navigate('/login?role=MANAGER')}>
                Manager
            </Button>
            </div>
        </div>
        </div>

    )
}