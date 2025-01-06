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
import { useNavigate, Link } from "react-router-dom"
import { AlertDestructive } from "./error"

function Modal({ show, onClose }: { show: boolean; onClose: () => void }) {
  if (!show) return null;

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
      <div className="bg-white p-6 rounded shadow-lg text-center">
        <h2 className="text-xl font-semibold">Success!</h2>
        <p>Login successfully! Redirecting to dashboard in a few seconds...</p>
        <Button onClick={onClose} className="mt-4">
          Close
        </Button>
      </div>
    </div>
  );
}


export function LoginForm({
  className,
  ...props
}: React.ComponentPropsWithoutRef<"div">) {

  const [user, setUser] = useState({
    username:' ',
    password: ' '
  });

  const [error, setError] = useState('');
  const [showModal, setShowModal] = useState(false);
  const navigate = useNavigate();
  const role = new URLSearchParams(window.location.search).get('role');


  const handleSubmit = (e: { preventDefault: () => void }) =>{
    e.preventDefault();
    setError('');
  fetch(`http://localhost:8080/users/login?role=${role}`, {
    method: "POST",
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(user),
  })
  .then(async(response) => { 
    if(!response.ok){
      const error = await response.text();
      throw new Error(error ||"Invalid Username or Password")
    }
    return response.text();
  })
  .then(token => {
    localStorage.setItem("token", token), setShowModal(true), 
    setTimeout(() => {
      setShowModal(false);
      navigate("/dashboard");
    }, 3000)
    })
  .catch(error => {console.error("Error occure: ", error), setError(error.message)});
  }

  const handleChange = (event: { target: { name: string; value: string } }) => {
    const {name, value} = event.target;
    setUser(previousUser => ({...previousUser, [name]: value}));
    setError('');
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
          <CardTitle className="text-2xl">Login</CardTitle>
          <CardDescription>
            Enter your email below to login to your account
          </CardDescription>
        </CardHeader>
        <CardContent>
      
          <form onSubmit={handleSubmit}>
            <div className="flex flex-col gap-6">
            {error && <AlertDestructive message={error} />}
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
                  <a
                    href="#"
                    className="ml-auto inline-block text-sm underline-offset-4 hover:underline"
                  >
                    Forgot your password?
                  </a>
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
                Login
              </Button>
            </div>
            <div className="mt-4 text-center text-sm">
              Don&apos;t have an account?{" "}
              <a href="/signup" className="underline underline-offset-4">
                Sign up
              </a>
            </div>
            <div className="mt-2 text-center text-sm">
              <Link to="/" className="underline underline-offset-4">
                Back to Homepage
              </Link>
            </div>
          </form>
        </CardContent>
      </Card>
    </div>
  )
}
