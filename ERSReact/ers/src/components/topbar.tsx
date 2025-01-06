import { ColorModeContext, tokens} from "../theme";
import {Box,IconButton, useTheme} from "@mui/material";
import { useContext } from "react";
import InputBase from "@mui/material/InputBase";
import LightModeOutlinedIcon from "@mui/icons-material/LightModeOutlined"
import DarkModeOutlinedIcon from "@mui/icons-material/DarkModeOutlined"
import NotificationOutlinedIcon from "@mui/icons-material/NotificationsOutlined"
import SettingsOutlinedIcon from "@mui/icons-material/SettingsOutlined"
import Person2OutlinedIcon from '@mui/icons-material/Person2Outlined';
import SearchIcon from "@mui/icons-material/Search"


const  Topbar = () => {
    const theme = useTheme();
    const colors = tokens(theme.palette.mode);
    const colorMode = useContext(ColorModeContext);

    return (
        <Box display = "flex" justifyContent="space-between" p={2}>
            <Box display="flex" sx={{backgroundColor:colors.primary[400], borderRadius:"3px"}}>
                <InputBase sx={{ml: 2, flex: 1}} placeholder="Search" />
                <IconButton type="button" sx={{p: 1}}>
                    <SearchIcon />
                </IconButton>
            </Box>
            <Box display="flex">
                <IconButton onClick={colorMode.toggleColorMode}>
                    {theme.palette.mode === 'dark' ? (
                        <LightModeOutlinedIcon />
                    ): (<DarkModeOutlinedIcon/> )}
               
                </IconButton>
                <IconButton>
                    <NotificationOutlinedIcon />
                </IconButton>
                <IconButton>
                    <SettingsOutlinedIcon />
                </IconButton>
                <IconButton>
                    <Person2OutlinedIcon />
                </IconButton>
            </Box>
        </Box>
    )
}

export default Topbar;