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
    TablePagination,
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

const SolvedProblemTable = () => {
    const setUser = useSetRecoilState(userState)

    const [page, setPage] = useState(0)
    const [data, setData] = useState([])
    const [ableNext, setAbleNext] = useState(false)

    useEffect(() => {
        ;(async () => {
            const token = window.localStorage.getItem('@token')
            if (token === null) {
                setUser({})
                return
            }

            const {
                data: {
                    code,
                    data: { hasNext, problems },
                },
            } = await axios.get(
                `${process.env.REACT_APP_SERVER_URL}/stat/solved-histories?offset=0`,
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                },
            )

            if (code === '0000') {
                console.log(hasNext)
                setData(problems)
                setAbleNext(hasNext)
            } else {
                setUser({})
            }
        })()
    }, [])

    const handleChangePage = async (event, newPage) => {
        const token = window.localStorage.getItem('@token')
        if (token === null) {
            setUser({})
            return
        }

        const {
            data: {
                code,
                data: { hasNext, problems },
            },
        } = await axios.get(
            `${process.env.REACT_APP_SERVER_URL}/stat/solved-histories?offset=${newPage}`,
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            },
        )

        if (code === '0000') {
            setData(problems)
            setAbleNext(hasNext)
        } else {
            setUser({})
        }
        setPage(newPage)
    }

    return (
        <Paper sx={{ width: '100%' }}>
            <TableContainer>
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
                            <TableRow key={problem.problemId} hover={true}>
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
                                            key={tag.tagName}
                                            label={tag.tagName}
                                            sx={{ mr: 1 }}
                                        />
                                    ))}
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                component="div"
                count={-1}
                rowsPerPage={5}
                page={page}
                labelDisplayedRows={() => null}
                labelRowsPerPage={() => null}
                rowsPerPageOptions={0}
                onPageChange={handleChangePage}
                backIconButtonProps={{ disabled: page === 0 }}
                nextIconButtonProps={{ disabled: !ableNext }}
            />
        </Paper>
    )
}

export default SolvedProblemTable
