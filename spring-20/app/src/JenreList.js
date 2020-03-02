import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import {Link} from 'react-router-dom';

class JenreList extends Component {

    constructor(props) {
        super(props);
        this.state = {jenres: [], isLoading: true};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('jenres')
            .then(response => response.json())
            .then(data => this.setState({jenres: data, isLoading: false}));
    }

    async remove(id) {
        await fetch(`/jenres/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedJenres = [...this.state.jenres].filter(i => i.id !== id);
            this.setState({jenres: updatedJenres});
        });
    }

    render() {
        const {jenres, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const jenreList = jenres.map(jenre => {
            return <tr key={jenre.id}>
                <td style={{whiteSpace: 'nowrap'}}>{jenre.type}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/jenres/" + jenre.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(jenre.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <Container fluid >
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/jenres/new">Add Jenre</Button>
                    </div>
                    <h3>Jenres</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th>Type</th>
                            <th width="10%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {jenreList}
                        </tbody>
                    </Table>
                    <Button color="link"><Link to="/">Back</Link></Button>
                </Container>
            </div>
        );
    }
}

export default JenreList;