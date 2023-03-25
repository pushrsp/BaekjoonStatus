import React, { useState, useEffect } from 'react'
import {
    Table,
    TableHead,
    TableRow,
    TableCell,
    TableContainer,
    Paper,
    TableBody,
    Chip,
} from '@mui/material'
import axios from 'axios'
import { useSetRecoilState } from 'recoil'

import { userState } from '../../atom'

const dailyProblemHeads = [
    {
        name: '레벨',
        width: 10,
    },
    {
        name: '제목',
        width: 200,
    },
    {
        name: '태그',
        width: 230,
    },
]

const DailyProblemTable = () => {
    const setUser = useSetRecoilState(userState)
    const [data, setData] = useState([])

    useEffect(() => {
        ;(async () => {
            const token = window.localStorage.getItem('@token')
            if (token === null) {
                setUser({})
                return
            }

            const {
                data: { code, data },
            } = await axios.get(`${process.env.REACT_APP_SERVER_URL}/stat/daily`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            })

            if (code === '0000') {
                setData(data)
            } else {
                setUser({})
            }
        })()
    }, [])

    return (
        <TableContainer component={Paper}>
            <Table>
                <TableHead>
                    {dailyProblemHeads.map((head) => (
                        <TableCell key={head.name} width={head.width} align="center">
                            {head.name}
                        </TableCell>
                    ))}
                </TableHead>
                <TableBody>
                    {data.map((problem) => (
                        <TableRow
                            key={problem.problemId}
                            hover={true}
                            style={{ cursor: 'pointer' }}
                            onClick={() =>
                                window.open(
                                    `https://www.acmicpc.net/problem/${problem.problemId}`,
                                    '_blank',
                                )
                            }>
                            <TableCell align="center">
                                <img
                                    style={{ width: 20, height: 20 }}
                                    loading="lazy"
                                    src={`https://static.solved.ac/tier_small/${problem.problemLevel}.svg`}
                                />
                            </TableCell>
                            <TableCell align="center">{problem.title}</TableCell>
                            <TableCell align="center">
                                {problem.tags.map((tag) => (
                                    <Chip
                                        color="info"
                                        key={tag.tag}
                                        label={tag.tag}
                                        sx={{ mr: 1 }}
                                    />
                                ))}
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    )
}

export default DailyProblemTable
