import React, { useState } from 'react'
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

const SolvedProblemTable = () => {
    const [page, setPage] = useState(0)

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
                        {SolvedProblems.map((data) => (
                            <TableRow key={data.problemId} hover={true}>
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
            <TablePagination
                component="div"
                count={-1}
                rowsPerPage={5}
                page={page}
                labelDisplayedRows={() => null}
                labelRowsPerPage={() => null}
                rowsPerPageOptions={0}
                onPageChange={() => console.log('HI')}
                // onRowsPerPageChange={handleChangeRowsPerPage}
            />
        </Paper>
    )
}

export default SolvedProblemTable
