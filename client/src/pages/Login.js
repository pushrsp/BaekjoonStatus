import React, { useState } from 'react'
import { Box, Avatar, Typography, TextField, useTheme, Button } from '@mui/material'
import LockOutlinedIcon from '@mui/icons-material/LockOutlined'
import { useNavigate } from 'react-router-dom'

import { tokens } from '../config/theme'

const Login = () => {
    const theme = useTheme()
    const colors = tokens(theme.palette.mode)
    const navigate = useNavigate()

    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')

    const onSubmit = (values) => {
        console.log(values)
    }

    return (
        <Box display="flex" width="100%" justifyContent="center" alignItems="center">
            <Box display="flex" flexDirection="column" alignItems="center">
                <Avatar sx={{ m: 1, bgcolor: colors.blueAccent['300'] }}>
                    <LockOutlinedIcon />
                </Avatar>
                <Typography component="h1" variant="h5">
                    로그인
                </Typography>
                <Box component="form" onSubmit={onSubmit} noValidate sx={{ mt: 1 }}>
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        label="아이디"
                        autoFocus
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        sx={{
                            '& label.Mui-focused': {
                                color: colors.grey['100'],
                            },
                            '& .MuiOutlinedInput-root': {
                                '&.Mui-focused fieldset': {
                                    borderColor: colors.blueAccent['200'],
                                },
                            },
                        }}
                    />
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        label="비밀번호"
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        sx={{
                            '& label.Mui-focused': {
                                color: colors.grey['100'],
                            },
                            '& .MuiOutlinedInput-root': {
                                '&.Mui-focused fieldset': {
                                    borderColor: colors.blueAccent['200'],
                                },
                            },
                        }}
                    />
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{
                            ':hover': { bgcolor: colors.blueAccent['200'] },
                            bgcolor: colors.blueAccent['300'],
                            mt: 2,
                        }}>
                        로그인
                    </Button>
                    <Button
                        fullWidth
                        variant="contained"
                        onClick={() =>
                            navigate('/signup', { replace: true, state: { from: 'login' } })
                        }
                        sx={{
                            ':hover': { bgcolor: colors.greenAccent['200'] },
                            bgcolor: colors.greenAccent['300'],
                            mt: 1,
                        }}>
                        회원가입
                    </Button>
                </Box>
            </Box>
        </Box>
    )
}

export default Login
