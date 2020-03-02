import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import Select from 'react-select'

class BookEdit extends Component {

    emptyItem = {
        title: '',
        author: '',
        jenre: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            authors: [],
            jenres: [],
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleAuthorsSelectChange = this.handleAuthorsSelectChange.bind(this);
        this.handleJenreSelectChange = this.handleJenreSelectChange.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const book = await (await fetch(`/books/${this.props.match.params.id}`)).json();
            this.setState({item: book});
        }

        const authors = await (await fetch(`/authors`)).json();
        this.setState({
            authors: authors.map(author => {
                const authorName = `${author.name || ''} ${author.patronymic || ''} ${author.surname || ''}`;
                return {value: author, label: authorName}
            })
        });

        const jenres = await (await fetch(`/jenres`)).json();
        this.setState({
            jenres: jenres.map(jenre => {
                const type = `${jenre.type || ''}`;
                return {value: jenre, label: type}
            })
        });
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    handleAuthorsSelectChange(option) {
        let item = {...this.state.item};
        item.author = option.value;
        this.setState({item});
    }

    handleJenreSelectChange(option) {
        let item = {...this.state.item};
        item.jenre = option.value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;

        await fetch(item.id ? '/books/' + item.id : '/books', {
            method: item.id ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/books');
    }

    render() {
        const {authors, jenres, item} = this.state;
        const title = <h2>{item.id ? 'Edit Book' : 'Add Book'}</h2>;

        return <div>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="title">Title</Label>
                        <Input type="text" name="title" id="title" value={item.title || ''}
                               onChange={this.handleChange} autoComplete="title"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="author">Author</Label>
                        <Select options={authors} onChange={this.handleAuthorsSelectChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="jenre">Jenre</Label>
                        <Select options={jenres} onChange={this.handleJenreSelectChange}/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/books">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(BookEdit);