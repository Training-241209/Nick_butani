'use client'
import { cn } from "@/lib/utils"
import { Button } from "@/components/ui/button"
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { AlertDestructive } from "./error"


function Modal({ show, onClose }: { show: boolean; onClose: () => void }) {
  if (!show) return null;

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
      <div className="bg-white p-6 rounded shadow-lg text-center">
        <h2 className="text-xl font-semibold">Success!</h2>
        <p>Account created successfully! Redirecting to login in a few seconds...</p>
        <Button onClick={onClose} className="mt-4">
          Close
        </Button>
      </div>
    </div>
  );
}


export function SignupForm({
  className,
  ...props
}: React.ComponentPropsWithoutRef<"div">) {
  const [user, setUser] = useState({
    firstname:' ',
    lastname:' ',
    username:' ',
    password:' '
  }
  )

  const [error, setError] = useState('');
  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);

  const handleSubmit = (e: { preventDefault: () => void }) => {
    e.preventDefault();
    setError('');

    fetch("http://localhost:8080/users/register", {
      method: "POST",
      headers: {
        'Content-Type': "application/json",
      },
      body: JSON.stringify(user),
    })
    .then(async(response) => { 
      if(!response.ok){
        const errorData = await response.json();
        throw new Error(errorData.message ||"User already exist!")
      }
      return response.json();
    })
    .then(data => {
      console.log('Account created! Redirecting ', 
      data), setShowModal(true), 
      setTimeout(() => {
        setShowModal(false);
        navigate("/login?role=EMPLOYEE");
      }, 3000)
      })
      .catch(error => {
        setError(error.message); 
        console.error("Error occurred: ", error);
      });
  };

  const handleChange = (event: { target: { name: string; value: string } }) => {
    const {name, value} = event.target;
    setUser(previousUser => ({...previousUser, [name]: value}));
  }

  return (
    
    <div className={cn("flex flex-col gap-6", className)} {...props}>
       <Modal
        show={showModal}
        onClose={() => {
          setShowModal(false);
          navigate("/login");
        }}
      />
      <Card>
        <CardHeader>
          <CardTitle className="text-2xl">SIGNUP</CardTitle>
          <CardDescription>
            Fill the form below to create account.
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit}>
            
            <div className="flex flex-col gap-6">
            {error && <AlertDestructive message={error} />}
            <div className="grid gap-2">
                <Label htmlFor="firstname">Firstname</Label>
                <Input
                  id="firstname"
                  type="text"
                  name="firstname"
                  placeholder="Firstname"
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="grid gap-2">
                <Label htmlFor="lastname">Lastname</Label>
                <Input
                  id="lastname"
                  type="text"
                  name="lastname"
                  placeholder="Lastname"
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="grid gap-2">
                <Label htmlFor="email">Username</Label>
                <Input
                  id="email"
                  type="text"
                  name="username"
                  onChange={handleChange}
                  placeholder="Username"
                  required
                />
              </div>
              <div className="grid gap-2">
                <div className="flex items-center">
                  <Label htmlFor="password">Password</Label>
                </div>
                <Input 
                  id="password" 
                  type="password" 
                  required
                  onChange={handleChange}
                  name="password"
                />
              </div>
              <Button type="submit" className="w-full">
                Signup
              </Button>
            </div>
            <div className="mt-4 text-center text-sm">
              Already have account?{" "}
              <a href="/login" className="underline underline-offset-4">
                Login
              </a>
            </div>
          </form>
        </CardContent>
      </Card>
    </div>
  )
}
