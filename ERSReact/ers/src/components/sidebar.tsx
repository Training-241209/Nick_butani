import { useEffect, useState } from "react";
import { ProSidebar, Menu, MenuItem } from "react-pro-sidebar";
import { Box, IconButton, Typography, useTheme } from "@mui/material";
import { Link, useNavigate} from "react-router-dom";
import "react-pro-sidebar/dist/css/styles.css";
import { tokens } from "../theme";
import HomeOutlinedIcon from "@mui/icons-material/HomeOutlined";
import ReceiptLongOutlinedIcon from "@mui/icons-material/ReceiptLongOutlined";
import MenuOutlinedIcon from "@mui/icons-material/MenuOutlined";
import { jwtDecode } from "jwt-decode";
import PersonIcon from "@mui/icons-material/Person"
import LogoutIcon from '@mui/icons-material/Logout';


interface ItemProps {
  title: string;
  to: string;
  icon: React.ReactElement;
  selected: string;
  setSelected: (title: string) => void;
}

interface decodedToken{
  firstname: string,
  lastname: string,
  role:string
}

const Item: React.FC<ItemProps> = ({ title, to, icon, selected, setSelected }) => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);

  return (
    <MenuItem
      active={selected === title}
      style={{
        color: colors.grey[100],
      }}
      onClick={() => setSelected(title)}
      icon={icon}
    >
      <Link to={to} style={{ textDecoration: "none", color: "inherit" }}>
        <Typography>{title}</Typography>
      </Link>
    </MenuItem>
  );
};


const Sidebar = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const [isCollapsed, setIsCollapsed] = useState(false);
  const navigate = useNavigate(); 
  const [selected, setSelected] = useState("Dashboard");
  const [user, setUser] = useState<decodedToken | null>(null);
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      try {
        const decoded = jwtDecode<decodedToken>(token);
        setUser(decoded);
      } catch (error) {
        console.error("Invalid token:", error);
      }
    }
  }, []);
  const initials = user ? `${user.firstname?.charAt(0) || ""}${user.lastname?.charAt(0) || ""}`.toUpperCase(): "";

  const handleLogout = () => {
    localStorage.removeItem("token"); 
    navigate("/");
  };

  return (
    <Box
      sx={{
        "& .pro-sidebar-inner": {
          background: `${colors.primary[400]} !important`,
        },
        "& .pro-icon-wrapper": {
          backgroundColor: "transparent !important",
        },
        "& .pro-inner-item": {
          padding: "5px 35px 5px 20px !important",
        },
        "& .pro-inner-item:hover": {
          color: "#868dfb !important",
        },
        "& .pro-menu-item.active": {
          color: "#6870fa !important",
        },
      }}
    >
      <ProSidebar collapsed={isCollapsed}>
        <Menu iconShape="square">
          {/* LOGO AND MENU ICON */}
          <MenuItem
            onClick={() => setIsCollapsed(!isCollapsed)}
            icon={isCollapsed ? <MenuOutlinedIcon /> : undefined}
            style={{
              margin: "10px 0 20px 0",
              color: colors.grey[100],
            }}
          >
            {!isCollapsed && (
              <Box
                display="flex"
                justifyContent="space-between"
                alignItems="center"
                ml="15px"
              >
                <Typography variant="h3" color={colors.grey[100]}>
                  ERS
                </Typography>
                <IconButton onClick={() => setIsCollapsed(!isCollapsed)}>
                  <MenuOutlinedIcon />
                </IconButton>
              </Box>
            )}
          </MenuItem>

          {!isCollapsed && (
            <Box mb="25px">
              <Box display="flex" justifyContent="center" alignItems="center">
              <Box
                width="80px"
                height="80px"
                display="flex"
                justifyContent="center"
                alignItems="center"
                fontSize="20px"
                fontWeight="bold"
                color="white"
                borderRadius="50%"
                border="2px solid"
              >
                {initials}
              </Box>
              </Box>
              <Box textAlign="center">
                {user ? (
                  <>
                    <Typography
                      variant="h4"
                      color={colors.grey[100]}
                      fontWeight="bold"
                      sx={{ m: "10px 0 0 0" }}
                    >
                      {`${user.firstname} ${user.lastname}`}
                    </Typography>
                    <Typography variant="h5" color={colors.greenAccent[500]}>
                      {user.role}
                    </Typography>
                  </>
                ) : (
                  <Typography variant="h6" color={colors.grey[100]}>
                    Loading user...
                  </Typography>
                )}
              </Box>

            </Box>
          )}

          <Box paddingLeft={isCollapsed ? undefined : "10%"}>
            <Item
              title="Dashboard"
              to="/dashboard"
              icon={<HomeOutlinedIcon />}
              selected={selected}
              setSelected={setSelected}
            />

            <Typography
              variant="h6"
              color={colors.grey[300]}
              sx={{ m: "15px 0 5px 20px" }}
            >
              Data
            </Typography>
            <Item
              title="Reimbursements"
              to="/reimburse"
              icon={<ReceiptLongOutlinedIcon />}
              selected={selected}
              setSelected={setSelected}
            />


            {user?.role === "MANAGER" && (
              <Item
                title="Employee"
                to="/users"
                icon={<PersonIcon />}
                selected={selected}
                setSelected={setSelected}
              />
            )}

            <MenuItem
              onClick={handleLogout}
              icon={<LogoutIcon />}
              style={{
                color: colors.grey[100],
                position: "absolute",
                bottom: "20px",
                width: "100%",
              }}
            >
              <Typography>Logout</Typography>
            </MenuItem>
       
          </Box>
        </Menu>
      </ProSidebar>
    </Box>
  );
};

export default Sidebar;