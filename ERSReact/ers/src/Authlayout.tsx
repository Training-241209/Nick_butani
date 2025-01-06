import { Outlet, Navigate} from 'react-router-dom';
import { ColorModeContext, useMode } from "../src/theme";
import { CssBaseline, ThemeProvider } from "@mui/material";
import Sidebar from "../src/components/sidebar";
import Topbar from "../src/components/topbar";

const AuthLayout = () => {
  const [theme, colorMode] = useMode();
  const token = localStorage.getItem("token");

  if (!token) {
    return <Navigate to="/login" replace />;
  }

  return (
    <ColorModeContext.Provider value={colorMode}>
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <div className="app" style={{ display: "flex", height: "100vh" }}>
          <Sidebar />
          <main className="content" style={{ flexGrow: 1, overflow: "auto" }}>
            <Topbar />
            <Outlet />
          </main>
        </div>
      </ThemeProvider>
    </ColorModeContext.Provider>
  );
};

export default AuthLayout;