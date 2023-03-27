import React, { useState, useEffect } from 'react'
import { Box, Button, TextField, Typography, useTheme } from '@mui/material'
import { useLocation, useNavigate } from 'react-router-dom'
import { toast } from 'react-toastify'
import axios from 'axios'
import Confetti from 'react-confetti'

import { tokens } from '../config/theme'

const Signup = () => {
    const theme = useTheme()
    const colors = tokens(theme.palette.mode)
    const { state } = useLocation()
    const navigate = useNavigate()

    useEffect(() => {
        if (state === null || state.from !== 'login') navigate('/', { replace: true })
    }, [])

    const [activeStep, setActiveStep] = useState(0)
    const [registerToken, setRegisterToken] = useState('')
    const [username, setUsername] = useState('')
    const [baekjoonUsername, setBaekjoonUsername] = useState('')
    const [password, setPassword] = useState('')

    const onClick = async () => {
        if (activeStep === 0) {
            const {
                data: { code, data, message },
            } = await axios.get(`${process.env.REACT_APP_SERVER_URL}/auth/baekjoon`, {
                params: { username: baekjoonUsername },
            })

            if (code === '0000') {
                toast.success(`총 ${data.solvedCount} 문제를 푸셨군요!`)
                setRegisterToken(data.registerToken)
                setActiveStep(1)
            } else {
                toast.error(message)
            }
        } else if (activeStep === 1) {
            const {
                data: { code, message },
            } = await axios.post(`${process.env.REACT_APP_SERVER_URL}/auth/signup`, {
                username,
                password,
                baekjoonUsername,
                registerToken,
            })

            if (code !== '0000') {
                toast.error(message)
            } else {
                setActiveStep(2)
            }
        } else {
            navigate('/', { replace: true })
        }
    }

    return (
        <Box display="flex" width="100%" justifyContent="center" alignItems="center">
            {activeStep === 2 && <Confetti />}
            <Box
                width="50%"
                display="flex"
                flexDirection="column"
                alignItems="center"
                sx={{
                    border: `1px solid ${
                        theme.palette.mode === 'dark'
                            ? colors.blueAccent['700']
                            : colors.grey['800']
                    }`,
                    borderRadius: 4,
                    boxShadow: `${colors.grey['800']} 0px 8px 24px`,
                }}>
                <Box>
                    {activeStep === 2 ? (
                        <Typography variant="h2">회원 가입을 축하드립니다</Typography>
                    ) : (
                        <Typography variant="h2">회원 가입</Typography>
                    )}
                </Box>
                <Box marginTop={1}>
                    {activeStep === 0 && <Typography variant="h5">백준 아이디 확인</Typography>}
                    {activeStep === 1 && <Typography variant="h5">비밀번호 입력</Typography>}
                </Box>
                <Box display="flex" flexDirection="column" width="40%">
                    {activeStep === 0 && (
                        <TextField
                            margin="normal"
                            required
                            label="백준 아이디"
                            autoFocus
                            value={baekjoonUsername}
                            onChange={(e) => setBaekjoonUsername(e.target.value)}
                            fullWidth
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
                    )}
                    {activeStep === 1 && (
                        <>
                            <TextField
                                margin="normal"
                                required
                                label="아이디"
                                autoFocus
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                fullWidth
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
                                label="비빌번호"
                                type="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                fullWidth
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
                        </>
                    )}
                    {activeStep === 2 && (
                        <Box
                            display="flex"
                            justifyContent="center"
                            alignItems="center"
                            style={{ fontSize: 150 }}>
                            🎉
                        </Box>
                    )}
                </Box>
                <Box display="flex" justifyContent="flex-end" width="80%" alignItems="center">
                    <Button
                        onClick={onClick}
                        variant="contained"
                        sx={{
                            ':hover': { bgcolor: colors.blueAccent['200'] },
                            bgcolor: colors.blueAccent['300'],
                            mb: 1,
                        }}>
                        {activeStep === 2 ? '로그인' : '다음'}
                    </Button>
                </Box>
            </Box>
        </Box>
    )
}

export default Signup
