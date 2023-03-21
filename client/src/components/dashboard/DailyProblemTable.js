import React from 'react'
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

import { SolvedProblems } from '../../data/mockData'

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
                    {SolvedProblems.map((data) => (
                        <TableRow
                            key={data.problemId}
                            hover={true}
                            style={{ cursor: 'pointer' }}
                            onClick={() =>
                                window.open(
                                    `https://www.acmicpc.net/problem/${data.problemId}`,
                                    '_blank',
                                )
                            }>
                            <TableCell align="center">
                                <img
                                    style={{ width: 20, height: 20 }}
                                    loading="lazy"
                                    src={`https://static.solved.ac/tier_small/${data.level}.svg`}
                                />
                            </TableCell>
                            <TableCell align="center">{data.title}</TableCell>
                            <TableCell align="center">
                                {data.tags.map((tag) => (
                                    <Chip
                                        color="info"
                                        key={tag.key}
                                        label={tag.key}
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
