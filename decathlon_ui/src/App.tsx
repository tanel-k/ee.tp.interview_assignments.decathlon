import React, {ChangeEvent} from 'react';
import {Box, CircularProgress, Container, InputAdornment, TextField} from "@material-ui/core";
import {Autocomplete} from "@material-ui/lab";
import {makeStyles} from "@material-ui/core/styles";
import {green} from "@material-ui/core/colors";

import EventTypeDto from "./domain/event-type.dto";
import {findAll, findScore} from "./api/event-type.api";
import makeCancelable from "./util/cancelable-promise";

const useStyles = makeStyles((theme) => ({
    loadingSpinner: {
        margin: 'auto'
    },
    field: {
        margin: theme.spacing(1),
        width: '100%'
    },
    score: {
        color: green.A700
    }
}));

const App = () => {
    const classes = useStyles();

    const [loading, setLoading] = React.useState(false);
    const [loadingScore, setLoadingScore] = React.useState(false);
    const [eventType, setEventType] = React.useState<EventTypeDto | null>(null);
    const [eventTypes, setEventTypes] = React.useState<EventTypeDto[]>([]);
    const [performance, setPerformance] = React.useState('');
    const [score, setScore] = React.useState<number | null>(null);

    const cancelGetScore = React.useRef<(() => void) | null>(null);

    React.useEffect(() => {
        setLoading(true);
        findAll()
            .then(({data}) => {
                setEventTypes(data);
            })
            .finally(() => {
                setLoading(false);
            });
    }, []);

    React.useEffect(() => {
        if (cancelGetScore.current) {
            cancelGetScore.current();
        }

        setScore(null);
        setLoadingScore(false);

        const sanitizedPerformance = performance?.trim().replace(',', '.');
        const performanceAsNumber = sanitizedPerformance
            ? Number(sanitizedPerformance)
            : NaN;
        if (eventType?.name && !Number.isNaN(performanceAsNumber) && Number.isFinite(performanceAsNumber)) {
            setLoadingScore(true);

            const {promise, cancel} = makeCancelable(findScore(eventType.name, performanceAsNumber));
            cancelGetScore.current = cancel;

            promise.then(({data}) => {
                setScore(data.score);
                setLoadingScore(false);
            }).catch(({canceled, message}) => {
                if (!canceled) {
                    setLoadingScore(false);
                }
            });
        }
    }, [eventType, performance]);

    const handleChangePerformance = (evt: ChangeEvent<any>) => {
        setPerformance(evt.target.value as string);
    };
    const handleChangeEventType = (evt: ChangeEvent<{}>, eventType: EventTypeDto | null) => {
        setEventType(eventType);
        setPerformance('');
    };
    const getOptionLabel = ({displayName}: EventTypeDto) => displayName;
    const getOptionSelected = ({name: nameA}: EventTypeDto, {name: nameB}: EventTypeDto) => nameA === nameB;
    const getPerformanceUnits = () => {
        if (!eventType) {
            return '';
        }

        return eventType.performanceDimension === 'TIME'
            ? 'seconds'
            : 'metres';
    };

    if (loading) {
        return (
            <Box p={1} display="flex" justifyContent="center" height="100vh">
                <CircularProgress className={classes.loadingSpinner} size={50} />
            </Box>
        );
    }

    return (
        <Container maxWidth="xs">
            <Box display="flex" flexDirection="column" m={1} p={1} justifyContent="center">
                <Box m="auto">
                    <h2>Decathlon Score Calculator</h2>
                </Box>
                <Box>
                    <Autocomplete
                        className={classes.field}
                        options={eventTypes}
                        multiple={false}
                        getOptionSelected={getOptionSelected}
                        onChange={handleChangeEventType}
                        value={eventType}
                        getOptionLabel={getOptionLabel}
                        renderInput={(params) => (
                            <TextField {...params} label="Event" variant="outlined" autoComplete="off" />
                        )}
                    />
                    <TextField
                        autoComplete="off"
                        className={classes.field}
                        value={performance}
                        onChange={handleChangePerformance}
                        InputProps={{
                            endAdornment: (
                                <InputAdornment position="end">
                                    {getPerformanceUnits()}
                                </InputAdornment>
                            )
                        }}
                        label="Performance"
                        variant="outlined"
                    />
                </Box>
                <Box m="auto">
                    {loadingScore && (
                        <CircularProgress />)
                    }
                    {score != null && (
                        <h3 className={classes.score}>{score} point{score > 1 || score === 0 ? 's' : ''}.</h3>
                    )}
                </Box>
            </Box>
        </Container>
    );
};

export default App;
